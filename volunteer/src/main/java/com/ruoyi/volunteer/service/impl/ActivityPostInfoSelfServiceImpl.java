package com.ruoyi.volunteer.service.impl;


import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.ActivityPostInfoSelfMapper;
import com.ruoyi.volunteer.domain.ActivityPostInfoSelf;
import com.ruoyi.volunteer.service.IActivityPostInfoSelfService;

/**
 * 活动信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-08-31
 */
@Service
public class ActivityPostInfoSelfServiceImpl implements IActivityPostInfoSelfService
{
    @Autowired
    private ActivityPostInfoSelfMapper activityPostInfoSelfMapper;

    /**
     * 查询活动信息
     *
     * @param activityId 活动信息主键
     * @return 活动信息
     */
    @Override
    public ActivityPostInfoSelf selectActivityPostInfoSelfByActivityId(Long activityId)
    {
        return activityPostInfoSelfMapper.selectActivityPostInfoSelfByActivityId(activityId);
    }

    /**
     * 查询活动信息列表
     *
     * @param activityPostInfoSelf 活动信息
     * @return 活动信息
     */
    @Override
    public List<ActivityPostInfoSelf> selectActivityPostInfoSelfList(ActivityPostInfoSelf activityPostInfoSelf)
    {
        return activityPostInfoSelfMapper.selectActivityPostInfoSelfList(activityPostInfoSelf);
    }

    /**
     * 新增活动信息
     *
     * @param activityPostInfoSelf 活动信息
     * @return 结果
     */
    @Override
    public int insertActivityPostInfoSelf(ActivityPostInfoSelf activityPostInfoSelf)
    {
        activityPostInfoSelf.setCreateTime(DateUtils.getNowDate());
        return activityPostInfoSelfMapper.insertActivityPostInfoSelf(activityPostInfoSelf);
    }

    /**
     * 修改活动信息
     *
     * @param activityPostInfoSelf 活动信息
     * @return 结果
     */
    @Override
    public int updateActivityPostInfoSelf(ActivityPostInfoSelf activityPostInfoSelf)
    {
        activityPostInfoSelf.setUpdateTime(DateUtils.getNowDate());
        return activityPostInfoSelfMapper.updateActivityPostInfoSelf(activityPostInfoSelf);
    }

    /**
     * 批量删除活动信息
     *
     * @param activityIds 需要删除的活动信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityPostInfoSelfByActivityIds(Long[] activityIds)
    {
        return activityPostInfoSelfMapper.deleteActivityPostInfoSelfByActivityIds(activityIds);
    }

    /**
     * 删除活动信息信息
     *
     * @param activityId 活动信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityPostInfoSelfByActivityId(Long activityId)
    {
        return activityPostInfoSelfMapper.deleteActivityPostInfoSelfByActivityId(activityId);
    }
}
