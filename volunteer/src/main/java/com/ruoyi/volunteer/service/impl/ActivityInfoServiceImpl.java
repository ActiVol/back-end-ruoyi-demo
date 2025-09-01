package com.ruoyi.volunteer.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.volunteer.domain.ActivityProcess;
import com.ruoyi.volunteer.domain.ActivityUserRecord;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;
import com.ruoyi.volunteer.mapper.ActivityUserRecordMapper;
import com.ruoyi.volunteer.service.IActivityProcessService;
import com.ruoyi.volunteer.service.IActivityUserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.ActivityInfoMapper;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.service.IActivityInfoService;
import org.springframework.util.CollectionUtils;

/**
 * 活动信息Service业务层处理
 *
 * @author guyue
 * @date 2024-12-13
 */
@Service
public class ActivityInfoServiceImpl implements IActivityInfoService {
    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    @Autowired
    private RedisCache redisCache;
    private static final String RECRUIT_NUMBER_KEY = "activity_recruit_number:";
//    @Autowired
//    private StateMachine<ActivityStatus, ActivityStatusChangeEvent> orderStateMachine;
//    @Autowired
//    private StateMachinePersister<ActivityStatus, ActivityStatusChangeEvent, String> stateMachineMemPersister;

    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private IActivityProcessService activityProcessService;
    @Autowired
    private IActivityUserRecordService activityUserRecordService;
    @Autowired
    private ActivityUserRecordMapper activityUserRecordMapper;


    /**
     * 活动报名
     *
     * @param activityId 活动ID
     * @return 报名返回信息
     * @throws ServiceException
     */
    @Override
    public AjaxResult signUp(Long activityId)  {
        try{
            Long userId = SecurityUtils.getUserId();
            Integer hasRecord = activityUserRecordMapper.selectUserInActivityRecord(userId + "", activityId + "");
            if (hasRecord!=0){
                return AjaxResult.warn("您已报名，无需重复提交！");
            }
            String username = SecurityUtils.getUsername();
            ActivityUserRecord activityUserRecord = new ActivityUserRecord();
            activityUserRecord.setUserId(userId);
            activityUserRecord.setUserName(username);
            activityUserRecord.setActivityId(activityId);
            activityUserRecord.setSignupTime(new Date());
            activityUserRecord.setParticipationStatus(Long.valueOf(1));
            activityUserRecordMapper.insert(activityUserRecord);
            return AjaxResult.success("报名成功！","报名成功！");
        }catch (Exception e){
            return AjaxResult.warn("报名失败！","报名失败！");
        }
    }

    @Override
    public List<ActivityInfo> getPublishActivities(ActivityInfo activityInfo) {
        activityInfo.setUserId(SecurityUtils.getUserId());
        return activityInfoMapper.getPublishActivities(activityInfo);
    }

    @Override
    public ParticipatedActivitiesVo getParticipatedActivities(ActivitySearchVo activitySearchVo) {
        ParticipatedActivitiesVo participatedActivitiesVo = new ParticipatedActivitiesVo();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        activitySearchVo.setEmail(user.getExternalEmail());
        activitySearchVo.setUsername(user.getUserName());
        if (user == null) {
            return participatedActivitiesVo;
        } else {
            Long userId = user.getUserId();
            List<ActivityInfo> activityInfos = activityInfoMapper.getParticipatedActivities(activitySearchVo);
            if (!CollectionUtils.isEmpty(activityInfos)) {
                long accumulatedDuration = activityInfos
                        .stream()
                        .filter(activityInfo -> activityInfo.getDuration() != null) // 过滤掉duration为null的情况
                        .mapToLong(ActivityInfo::getDuration) // 将每个 ActivityInfo 转换成其 duration
                        .sum();
                participatedActivitiesVo.setActivityInfo(activityInfos);
                participatedActivitiesVo.setPartivipatedCount(activityInfos.size());
                participatedActivitiesVo.setAccumulatedDuration(accumulatedDuration);
            }
            String username = user.getUserName();
            String email = user.getExternalEmail();
            String avatar = user.getAvatar();
            participatedActivitiesVo.setEmail(email);
            participatedActivitiesVo.setUsername(username);
            participatedActivitiesVo.setAvatar(avatar);
            participatedActivitiesVo.setUserId(userId);
            return participatedActivitiesVo;
        }
    }



    /**
     * 查询活动信息
     *
     * @param activityId 活动信息主键
     * @return 活动信息
     */
    @Override
    public ActivityInfo selectActivityInfoByActivityId(Long activityId) {
        ActivityInfo activityInfo = activityInfoMapper.selectActivityInfoByActivityId(activityId);
        // 审批记录
        List<ActivityProcess> activityProcessByActivityId = activityProcessService.getActivityProcessByActivityId(activityId);
        activityInfo.setReviewLog(activityProcessByActivityId);
        // 报名用户
        LambdaQueryWrapper<ActivityUserRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActivityUserRecord::getActivityId, activityId);
        lqw.orderByAsc(ActivityUserRecord::getSignupTime);
        List<ActivityUserRecord> activityUserRecords = activityUserRecordMapper.selectList(lqw);
        activityInfo.setSignUpUsers(activityUserRecords);
        return activityInfo;
    }

    /**
     * 查询活动信息列表
     *
     * @param activityInfo 活动信息
     * @return 活动信息
     */
    @Override
    public List<ActivityInfo> selectActivityInfoList(ActivityInfo activityInfo) {
        return activityInfoMapper.selectActivityInfoList(activityInfo);
    }

    @Override
    public List<ActivityInfo> selectActivityProcessList(ActivityInfo activityInfo) {
        return activityInfoMapper.selectActivityProcessList(activityInfo);
    }

    /**
     * 新增活动信息
     *
     * @param activityInfo 活动信息
     * @return 结果
     */
    @Override
    public int insertActivityInfo(ActivityInfo activityInfo) {
        Long deptId = SecurityUtils.getDeptId();
        activityInfo.setActivityStatus("1");
        activityInfo.setPublishStatus("1");
        activityInfo.setDeptId(deptId);
        activityInfo.setCreateBy(SecurityUtils.getUserId()+"");
        activityInfo.setCreateTime(DateUtils.getNowDate());
        activityInfo.setUpdateBy(SecurityUtils.getUserId()+"");
        activityInfo.setUpdateTime(DateUtils.getNowDate());
        return activityInfoMapper.insert(activityInfo);
    }

    /**
     * 修改活动信息
     *
     * @param activityInfo 活动信息
     * @return 结果
     */
    @Override
    public int updateActivityInfo(ActivityInfo activityInfo) {
        return activityInfoMapper.updateActivityInfo(activityInfo);
    }


    /**
     * 批量删除活动信息
     *
     * @param activityIds 需要删除的活动信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityInfoByActivityIds(Long[] activityIds) {
        return activityInfoMapper.deleteActivityInfoByActivityIds(activityIds);
    }

    /**
     * 删除活动信息信息
     *
     * @param activityId 活动信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityInfoByActivityId(Long activityId) {
        return activityInfoMapper.deleteActivityInfoByActivityId(activityId);
    }




    @Override
    public int updateActivityStatus(Long activityId, String status){
        // 未提交1、待审核2、已通过3、已驳回4
        LambdaUpdateWrapper<ActivityInfo> luw = new LambdaUpdateWrapper<>();
        luw.eq(ActivityInfo::getActivityId, activityId)
                .set(ActivityInfo::getActivityStatus, status);
        return activityInfoMapper.update(null, luw);
    }

    @Override
    public int updateActivityPublishStatus(Long activityId, String publishStatus) {
        // 发布状态：未通过1、未发布2、已发布3、已结束4
        LambdaUpdateWrapper<ActivityInfo> luw = new LambdaUpdateWrapper<>();
        luw.eq(ActivityInfo::getActivityId, activityId);
        luw.set(ActivityInfo::getPublishStatus, publishStatus);
        if ("3".equals(publishStatus)){
            luw.set(ActivityInfo::getPublishTime, DateUtils.getNowDate());
        }
        return activityInfoMapper.update(null, luw);
    }

    @Override
    public AjaxResult commitActivityInfo(Long activityId,String reviewComments) {
        String status = "2";
        String[] roles = {"eventOrganizer","ordinaryAdmin","admin"};
        try{
//            System.out.println(SecurityUtils.getLoginUser().getUser().getRoles());
            boolean eventOrganizer = SecurityUtils.hasAnyOneRoles(roles);
            if (!eventOrganizer) {
                return AjaxResult.warn("您没有权限进行此操作");
            }
            String msg = updateActivityStatus(activityId, status)>0?"提交成功":"未找到对应记录";
            if ("提交成功".equals(msg)){
                ActivityProcess activityProcess = new ActivityProcess(SecurityUtils.getUsername(), DateUtils.getNowDate(), reviewComments, status, activityId);
                activityProcessService.insertActivityProcess(activityProcess);
                // 审核通过后，修改活动发布状态为待发布、
                updateActivityPublishStatus(activityId, "2");
            }
            return AjaxResult.success(msg);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.warn("您没有权限进行此操作");
        }
    }


    @Override
    public AjaxResult processActivityInfo(Long activityId, String reviewStatus,String reviewComments) throws Exception {
        String status = "3";
        String[] roles = {"ordinaryAdmin"};
        try{
            System.out.println(SecurityUtils.getLoginUser().getUser().getRoles());
            boolean eventOrganizer = SecurityUtils.hasAnyOneRoles(roles);
            if (!eventOrganizer) {
                return AjaxResult.warn("您没有权限进行此操作");
            }
            // 同意
            if ("1".equals(reviewStatus)){
                String msg = updateActivityStatus(activityId, status)>0?"提交成功":"未找到对应记录";
                if ("提交成功".equals(msg)){
                    ActivityProcess activityProcess = new ActivityProcess(SecurityUtils.getUsername(), DateUtils.getNowDate(), reviewComments, status, activityId);
                    activityProcessService.insertActivityProcess(activityProcess);
                }
                return AjaxResult.success(msg);
            }
            // 驳回
            if ("2".equals(reviewStatus)){
                status = "4";
                String msg = updateActivityStatus(activityId, status)>0?"提交成功":"未找到对应记录";
                if ("提交成功".equals(msg)){
                    ActivityProcess activityProcess = new ActivityProcess(SecurityUtils.getUsername(), DateUtils.getNowDate(), reviewComments, status, activityId);
                    activityProcessService.insertActivityProcess(activityProcess);
                }
                return AjaxResult.success(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.warn("操作失败");
        }
        return AjaxResult.success();
    }

    @Override
    public int publishActivityInfo(Long activityId) {
        return updateActivityPublishStatus(activityId, "3");
    }

    @Override
    public int overActivityInfo(Long activityId) {
        // todo 修改活动状态为已结束
        int i = updateActivityPublishStatus(activityId, "4");
        // todo 为所有参加活动的用户分发参与时长
            // 1. 查询活动信息

        return i ;
    }


    // 查询组织者的活动列表activity_status  待提交 待审核  已通过


    @Override
    public List<ActivityInfo> getAuditActivityList(String activityStatus) {
        List<SysRole> roleList = SecurityUtils.getLoginUser().getUser().getRoles();
        System.out.println("当前用户角色为：" + roleList);
        String[] roles1 = {"ordinaryAdmin","admin"};
        boolean isOrdinaryAdmin = SecurityUtils.hasAnyOneRoles(roles1);
        String[] roles2 = {"eventOrganizer"};
        boolean isOrganizer = SecurityUtils.hasAnyOneRoles(roles2);
//        LambdaQueryWrapper<ActivityInfo> lqw = new LambdaQueryWrapper<>();
//        if ("1".equals(lqw.eq(ActivityInfo::getActivityStatus, activityStatus))){
//            lqw.in(ActivityInfo::getActivityStatus, "1","4");
//        }else {
//            lqw.eq(ActivityInfo::getActivityStatus, activityStatus);
//        }
        if (isOrdinaryAdmin){ // 辅导员
            return activityInfoMapper.getAuditActivityList(activityStatus,null);
        }else if (!isOrdinaryAdmin && isOrganizer){  // 组织者
            return activityInfoMapper.getAuditActivityList(activityStatus,SecurityUtils.getUserId()+"");
        }else {
            return new ArrayList<>();
        }
//        return activityInfoMapper.selectList(lqw);
    }
}
