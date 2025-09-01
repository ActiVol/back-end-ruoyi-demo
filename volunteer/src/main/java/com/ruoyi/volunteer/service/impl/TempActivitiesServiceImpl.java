package com.ruoyi.volunteer.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.File;
import com.ruoyi.volunteer.service.IActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.TempActivitiesMapper;
import com.ruoyi.volunteer.domain.TempActivities;
import com.ruoyi.volunteer.service.ITempActivitiesService;

/**
 * 临时活动Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class TempActivitiesServiceImpl implements ITempActivitiesService {
    @Autowired
    private TempActivitiesMapper tempActivitiesMapper;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 临时活动审核
     *
     * @param id
     * @return
     */
    @Override
    public Boolean processTempActivity(Long id) {
        TempActivities tempActivities = tempActivitiesMapper.selectTempActivitiesById(id);
        SysUser sysUser = new SysUser();
        String userName = tempActivities.getUserName();
        String externalEmail = tempActivities.getExternalEmail();
        String internalEmail = tempActivities.getInternalEmail();
        String graduationYear = tempActivities.getGraduationYear();
        String organizerName = tempActivities.getOrganizerName();
        String organizerEmail = tempActivities.getOrganizerEmail();
        String activityName = tempActivities.getActivityName();
        String activityAddress = tempActivities.getActivityAddress();
        String activityDetails = tempActivities.getActivityDetails();
        Long activityDuration = tempActivities.getActivityDuration();
        Integer recruitNumber = tempActivities.getRecruitNumber();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        SysDept sysDept = new SysDept();
        String deptName = organizerName + "的部门";
        sysDept.setDeptName(deptName);
        sysDept.setLeader(organizerName);
        sysDept.setStatus("0");
        sysDept.setDelFlag("0");
        sysDept.setEmail(organizerEmail);
        sysDeptService.insertDept(sysDept);
        Long deptId = sysDept.getDeptId();
        sysUser.setDeptId(deptId);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        sysUser.setGraduationYear(graduationYear);
        sysUser.setDurationOfQualify(0L);
        sysUser.setUserName(userName);
        sysUser.setExternalEmail(externalEmail);
        sysUser.setInternalEmail(internalEmail);
        sysUser.setGraduationYear(graduationYear);
        // 默认部门
        Long[] postIds = {4L};
        // 默认角色
        Long[] roleIds = {2L};
        sysUser.setPostIds(postIds);
        sysUser.setRoleIds(roleIds);
        sysUserService.registerUser(sysUser);
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setActivityStatus("0");
        activityInfo.setActivityName(activityName);
        activityInfo.setAddress(activityAddress);
        activityInfo.setDetails(activityDetails);
        activityInfo.setDuration(activityDuration);
        activityInfo.setDeptId(deptId);
        activityInfo.setActivityStatus("5");
        activityInfo.setPublishStatus("1");
        activityInfo.setStartTime(DateUtils.getNowDate());
        activityInfo.setRecruitNumber(recruitNumber);
        activityInfoService.insertActivityInfo(activityInfo);
        tempActivities.setReviewStatus("2");
        tempActivitiesMapper.updateTempActivities(tempActivities);
        return true;
    }


    /**
     * 查询临时活动
     *
     * @param id 临时活动主键
     * @return 临时活动
     */
    @Override
    public TempActivities selectTempActivitiesById(Long id) {
        return tempActivitiesMapper.selectTempActivitiesById(id);
    }

    /**
     * 查询临时活动列表
     *
     * @param tempActivities 临时活动
     * @return 临时活动
     */
    @Override
    public List<TempActivities> selectTempActivitiesList(TempActivities tempActivities) {
        return tempActivitiesMapper.selectTempActivitiesList(tempActivities);
    }

    /**
     * 新增临时活动
     *
     * @param tempActivities 临时活动
     * @return 结果
     */
    @Override
    public int insertTempActivities(TempActivities tempActivities) {
        tempActivities.setReviewStatus("1");
        return tempActivitiesMapper.insertTempActivities(tempActivities);
    }

    /**
     * 修改临时活动
     *
     * @param tempActivities 临时活动
     * @return 结果
     */
    @Override
    public int updateTempActivities(TempActivities tempActivities) {
        return tempActivitiesMapper.updateTempActivities(tempActivities);
    }

    /**
     * 批量删除临时活动
     *
     * @param ids 需要删除的临时活动主键
     * @return 结果
     */
    @Override
    public int deleteTempActivitiesByIds(Long[] ids) {
        return tempActivitiesMapper.deleteTempActivitiesByIds(ids);
    }

    /**
     * 删除临时活动信息
     *
     * @param id 临时活动主键
     * @return 结果
     */
    @Override
    public int deleteTempActivitiesById(Long id) {
        return tempActivitiesMapper.deleteTempActivitiesById(id);
    }


}
