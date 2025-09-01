package com.ruoyi.volunteer.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;

/**
 * 活动信息Service接口
 *
 * @author guyue
 * @date 2024-12-13
 */
public interface IActivityInfoService {
    /**
     * 查询活动信息
     *
     * @param activityId 活动信息主键
     * @return 活动信息
     */
    public ActivityInfo selectActivityInfoByActivityId(Long activityId);

    /**
     * 查询活动信息列表
     *
     * @param activityInfo 活动信息
     * @return 活动信息集合
     */
    public List<ActivityInfo> selectActivityInfoList(ActivityInfo activityInfo);

    /**
     * 查询审核信息列表
     *
     * @param activityInfo 审核信息
     * @return 审核信息集合
     */
    public List<ActivityInfo> selectActivityProcessList(ActivityInfo activityInfo);

    /**
     * 新增活动信息
     *
     * @param activityInfo 活动信息
     * @return 结果
     */
    public int insertActivityInfo(ActivityInfo activityInfo);

    /**
     * 修改活动信息
     *
     * @param activityInfo 活动信息
     * @return 结果
     */
    public int updateActivityInfo(ActivityInfo activityInfo);


    /**
     * 批量删除活动信息
     *
     * @param activityIds 需要删除的活动信息主键集合
     * @return 结果
     */
    public int deleteActivityInfoByActivityIds(Long[] activityIds);

    /**
     * 删除活动信息信息
     *
     * @param activityId 活动信息主键
     * @return 结果
     */
    public int deleteActivityInfoByActivityId(Long activityId);

    /**
     * 活动报名
     *
     * @param activityId 活动ID
     * @return
     */
    public AjaxResult signUp(Long activityId);

    /**
     * 获取发布的活动
     *
     * @return
     */
    List<ActivityInfo> getPublishActivities(ActivityInfo activityInfo);

    /**
     * 获取参加过的活动信息
     *
     * @param activitySearchVo
     * @return
     */
    ParticipatedActivitiesVo getParticipatedActivities(ActivitySearchVo activitySearchVo);


    /**
     * 更新活动状态
     */
    int updateActivityStatus(Long activityId, String status);

    /**
     * 更新发布状态
     */
    int updateActivityPublishStatus(Long activityId, String publishStatus);

    /**
     * 提交活动信息
     */
    AjaxResult commitActivityInfo(Long activityId,String reviewComments);

    /**
     * 审核活动信息
     */
    AjaxResult processActivityInfo(Long activityId, String reviewStatus,String reviewComments) throws Exception;

    /**
     * 发布活动信息
     * @param activityId 活动ID
     * @return 结果
     */
    public int publishActivityInfo(Long activityId);

    /**
     * 结束活动信息
     * @param activityId 活动ID
     * @return 结果
     */
    public int overActivityInfo(Long activityId);


    /**
     * 查询活动审核列表activity_status  待提交1 待审核 2 已通过3
     */
    List<ActivityInfo> getAuditActivityList(String activityStatus);

}
