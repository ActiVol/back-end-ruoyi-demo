package com.ruoyi.volunteer.service;


import java.util.List;
import com.ruoyi.volunteer.domain.ActivityPostInfoSelf;

/**
 * 活动信息Service接口
 *
 * @author ruoyi
 * @date 2025-08-31
 */
public interface IActivityPostInfoSelfService
{
    /**
     * 查询活动信息
     *
     * @param activityId 活动信息主键
     * @return 活动信息
     */
    public ActivityPostInfoSelf selectActivityPostInfoSelfByActivityId(Long activityId);

    /**
     * 查询活动信息列表
     *
     * @param activityPostInfoSelf 活动信息
     * @return 活动信息集合
     */
    public List<ActivityPostInfoSelf> selectActivityPostInfoSelfList(ActivityPostInfoSelf activityPostInfoSelf);

    /**
     * 新增活动信息
     *
     * @param activityPostInfoSelf 活动信息
     * @return 结果
     */
    public int insertActivityPostInfoSelf(ActivityPostInfoSelf activityPostInfoSelf);

    /**
     * 修改活动信息
     *
     * @param activityPostInfoSelf 活动信息
     * @return 结果
     */
    public int updateActivityPostInfoSelf(ActivityPostInfoSelf activityPostInfoSelf);

    /**
     * 批量删除活动信息
     *
     * @param activityIds 需要删除的活动信息主键集合
     * @return 结果
     */
    public int deleteActivityPostInfoSelfByActivityIds(Long[] activityIds);

    /**
     * 删除活动信息信息
     *
     * @param activityId 活动信息主键
     * @return 结果
     */
    public int deleteActivityPostInfoSelfByActivityId(Long activityId);
}
