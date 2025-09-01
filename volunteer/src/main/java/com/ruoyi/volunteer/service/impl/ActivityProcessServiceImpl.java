package com.ruoyi.volunteer.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.service.IActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.ActivityProcessMapper;
import com.ruoyi.volunteer.domain.ActivityProcess;
import com.ruoyi.volunteer.service.IActivityProcessService;

/**
 * 审核信息Service业务层处理
 *
 * @author guyue
 * @date 2024-12-13
 */
@Service
public class ActivityProcessServiceImpl implements IActivityProcessService {
    @Autowired
    private ActivityProcessMapper activityProcessMapper;
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 查询审核信息
     *
     * @param processId 审核信息主键
     * @return 审核信息
     */
    @Override
    public ActivityProcess selectActivityProcessByProcessId(Long processId) {
        return activityProcessMapper.selectActivityProcessByProcessId(processId);
    }

    /**
     * 查询审核信息列表
     *
     * @param activityProcess 审核信息
     * @return 审核信息
     */
    @Override
    public List<ActivityProcess> selectActivityProcessList(ActivityProcess activityProcess) {
        return activityProcessMapper.selectActivityProcessList(activityProcess);
    }

    /**
     * 新增审核信息
     *
     * @param activityProcess 审核信息
     * @return 结果
     */
    @Override
    public int insertActivityProcess(ActivityProcess activityProcess) {
        return activityProcessMapper.insertActivityProcess(activityProcess);
    }

    /**
     * 修改审核信息
     *
     * @param activityProcess 审核信息
     * @return 结果
     */
    @Override
    public int updateActivityProcess(ActivityProcess activityProcess) {
        return activityProcessMapper.updateActivityProcess(activityProcess);
    }

    /**
     * 批量删除审核信息
     *
     * @param processIds 需要删除的审核信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityProcessByProcessIds(Long[] processIds) {
        return activityProcessMapper.deleteActivityProcessByProcessIds(processIds);
    }

    /**
     * 删除审核信息信息
     *
     * @param processId 审核信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityProcessByProcessId(Long processId) {
        return activityProcessMapper.deleteActivityProcessByProcessId(processId);
    }

    /**
     * 批阅审核信息
     *
     * @param activityProcess
     * @return
     */
    @Override
    public int processActivity(ActivityProcess activityProcess) throws Exception {
        Long activityId = activityProcess.getActivityId();
        String reviewStatus = activityProcess.getReviewStatus();
        if (StringUtils.isEmpty(reviewStatus)) {
            throw new Exception("审核状态不能为空");
        } else if (!"1".equals(reviewStatus) && !"2".equals(reviewStatus)) {
            throw new Exception("审核状态不合规");
        }
        String publishStatus = "2".equals(reviewStatus) ? "4" : "3";
        String activityStatus = "2".equals(reviewStatus) ? "1" : "4";
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setActivityId(activityId);
        activityInfo.setPublishStatus(publishStatus);
        activityInfo.setActivityStatus(activityStatus);
        activityInfoService.updateActivityInfo(activityInfo);
        activityProcess.setReviewTime(DateUtils.getNowDate());
        activityProcess.setReviewer(SecurityUtils.getUsername());
        return activityProcessMapper.insertActivityProcess(activityProcess);
    }


    @Override
    public List<ActivityProcess> getActivityProcessByActivityId(Long activityId) {
        LambdaQueryWrapper<ActivityProcess> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActivityProcess::getActivityId, activityId);
        lqw.orderByAsc(ActivityProcess::getReviewTime);
        List<ActivityProcess> activityProcesses = activityProcessMapper.selectList(lqw);
        if (activityProcesses==null){
            return new ArrayList<>();
        }
        return activityProcesses;
    }
}
