package com.ruoyi.volunteer.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.volunteer.domain.EmailSendLog;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.ActivityUserRecordMapper;
import com.ruoyi.volunteer.domain.ActivityUserRecord;
import com.ruoyi.volunteer.service.IActivityUserRecordService;

/**
 * 用户报名活动记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-07-13
 */
@Service
public class ActivityUserRecordServiceImpl implements IActivityUserRecordService
{
    @Autowired
    private ActivityUserRecordMapper activityUserRecordMapper;

    @Autowired
    private EmailSendLogServiceImpl emailSendLogService;

    /**
     * 查询用户报名活动记录
     *
     * @param id 用户报名活动记录主键
     * @return 用户报名活动记录
     */
    @Override
    public ActivityUserRecord selectActivityUserRecordById(Long id)
    {
        return activityUserRecordMapper.selectActivityUserRecordById(id);
    }

    /**
     * 查询用户报名活动记录列表
     *
     * @param activityUserRecord 用户报名活动记录
     * @return 用户报名活动记录
     */
    @Override
    public List<ActivityUserRecord> selectActivityUserRecordList(ActivityUserRecord activityUserRecord)
    {
        return activityUserRecordMapper.selectActivityUserRecordList(activityUserRecord);
    }

    /**
     * 新增用户报名活动记录
     *
     * @param activityUserRecord 用户报名活动记录
     * @return 结果
     */
    @Override
    public int insertActivityUserRecord(ActivityUserRecord activityUserRecord)
    {

        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        ActivityUserRecord signUpInfo = selectSignUpInfo(activityUserRecord.getActivityId() + "", userId + "");
        if (signUpInfo!=null){
            return 0;
        }
        activityUserRecord.setUserId(userId);
        activityUserRecord.setUserName(username);
        activityUserRecord.setSignupTime(new Date());
        return activityUserRecordMapper.insertActivityUserRecord(activityUserRecord);
    }

    /**
     * 修改用户报名活动记录
     *
     * @param activityUserRecord 用户报名活动记录
     * @return 结果
     */
    @Override
    public int updateActivityUserRecord(ActivityUserRecord activityUserRecord)
    {
        return activityUserRecordMapper.updateActivityUserRecord(activityUserRecord);
    }

    /**
     * 批量删除用户报名活动记录
     *
     * @param ids 需要删除的用户报名活动记录主键
     * @return 结果
     */
    @Override
    public int deleteActivityUserRecordByIds(Long[] ids)
    {
        return activityUserRecordMapper.deleteActivityUserRecordByIds(ids);
    }

    /**
     * 删除用户报名活动记录信息
     *
     * @param id 用户报名活动记录主键
     * @return 结果
     */
    @Override
    public int deleteActivityUserRecordById(Long id)
    {
        return activityUserRecordMapper.deleteActivityUserRecordById(id);
    }


    @Override
    public ActivityUserRecord selectSignUpInfo(String activityId, String userId) {
        LambdaQueryWrapper<ActivityUserRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActivityUserRecord::getUserId, userId);
        lqw.eq(ActivityUserRecord::getActivityId, activityId);
        ActivityUserRecord signUpInfo = activityUserRecordMapper.selectOne(lqw);
        return signUpInfo;
    }

    @Override
    public AjaxResult signUpPass(String activityId, String userId) {
        ActivityUserRecord signUpInfo = selectSignUpInfo(activityId, userId);
        if (signUpInfo == null){
            return AjaxResult.error("未找到该用户报名信息");
        }
        // 待确认1、已确认2、拒绝3、已取消4
        LambdaUpdateWrapper<ActivityUserRecord> luw = new LambdaUpdateWrapper<>();
        luw.eq(ActivityUserRecord::getActivityId, activityId)
                .eq(ActivityUserRecord::getUserId, userId)
                .set(ActivityUserRecord::getParticipationStatus, 2);
        activityUserRecordMapper.update(null, luw);
        EmailSendLog emailSendLog = new EmailSendLog();
        emailSendLog.setReceiverUserId(userId);
        try {
            emailSendLogService.sendEmailToOrganizer(emailSendLog, activityId, true);
        }catch (MailParseException e){
            return AjaxResult.error("邮件发送失败,请检查用户邮箱是否正确。");
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult signUpReject(String activityId, String userId) {
        ActivityUserRecord signUpInfo = selectSignUpInfo(activityId, userId);
        if (signUpInfo == null){
            return AjaxResult.error("未找到该用户报名信息");
        }
        // 待确认1、已确认2、拒绝3、已取消4
        LambdaUpdateWrapper<ActivityUserRecord> luw = new LambdaUpdateWrapper<>();
        luw.eq(ActivityUserRecord::getActivityId, activityId)
                .eq(ActivityUserRecord::getUserId, userId)
                .set(ActivityUserRecord::getParticipationStatus, 3);
        activityUserRecordMapper.update(null, luw);
        EmailSendLog emailSendLog = new EmailSendLog();
        emailSendLog.setReceiverUserId(userId);
        emailSendLogService.sendEmailToOrganizer(emailSendLog,activityId, false);
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult signUpCancel(String activityId, String userId) {
        ActivityUserRecord signUpInfo = selectSignUpInfo(activityId, userId);
        if (signUpInfo == null){
            return AjaxResult.error("未找到该用户报名信息");
        }
        // 待确认1、已确认2、拒绝3、已取消4
        LambdaUpdateWrapper<ActivityUserRecord> luw = new LambdaUpdateWrapper<>();
        luw.eq(ActivityUserRecord::getActivityId, activityId)
                .eq(ActivityUserRecord::getUserId, userId)
                .set(ActivityUserRecord::getParticipationStatus, 4);
        activityUserRecordMapper.update(null, luw);
        return AjaxResult.success("操作成功");
    }


    @Override
    public List<Map<String, String>> zzzPendingReviewList() {
        try{
            Long userId = SecurityUtils.getUserId();
            List<Map<String, String>> maps = activityUserRecordMapper.zzzPendingReviewList(String.valueOf(userId));
            return maps;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
