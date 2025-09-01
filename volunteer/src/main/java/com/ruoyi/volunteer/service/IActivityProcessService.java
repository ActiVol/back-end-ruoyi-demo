package com.ruoyi.volunteer.service;

import java.util.List;

import com.ruoyi.volunteer.domain.ActivityProcess;

/**
 * 审核信息Service接口
 *
 * @author guyue
 * @date 2024-12-13
 */
public interface IActivityProcessService {
    /**
     * 查询审核信息
     *
     * @param processId 审核信息主键
     * @return 审核信息
     */
    public ActivityProcess selectActivityProcessByProcessId(Long processId);

    /**
     * 查询审核信息列表
     *
     * @param activityProcess 审核信息
     * @return 审核信息集合
     */
    public List<ActivityProcess> selectActivityProcessList(ActivityProcess activityProcess);

    /**
     * 新增审核信息
     *
     * @param activityProcess 审核信息
     * @return 结果
     */
    public int insertActivityProcess(ActivityProcess activityProcess);

    /**
     * 修改审核信息
     *
     * @param activityProcess 审核信息
     * @return 结果
     */
    public int updateActivityProcess(ActivityProcess activityProcess);

    /**
     * 批量删除审核信息
     *
     * @param processIds 需要删除的审核信息主键集合
     * @return 结果
     */
    public int deleteActivityProcessByProcessIds(Long[] processIds);

    /**
     * 删除审核信息信息
     *
     * @param processId 审核信息主键
     * @return 结果
     */
    public int deleteActivityProcessByProcessId(Long processId);

    /**
     * 批阅审核信息
     *
     * @param activityProcess
     * @return
     */
    public int processActivity(ActivityProcess activityProcess) throws Exception;


    /**
     * 根据活动ID获取审核记录
     * @param activityId 活动ID
     * @return list
     */
    public List<ActivityProcess> getActivityProcessByActivityId(Long activityId);
}
