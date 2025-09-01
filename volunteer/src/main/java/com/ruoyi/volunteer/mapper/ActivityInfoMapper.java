package com.ruoyi.volunteer.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;
import org.apache.ibatis.annotations.Param;

/**
 * 活动信息Mapper接口
 *
 * @author guyue
 * @date 2024-12-13
 */
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {
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
     * 删除活动信息
     *
     * @param activityId 活动信息主键
     * @return 结果
     */
    public int deleteActivityInfoByActivityId(Long activityId);

    /**
     * 批量删除活动信息
     *
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityInfoByActivityIds(Long[] activityIds);

    /**
     * 发布活动信息
     *
     * @param activityInfo
     * @return
     */
    int publishActivityInfo(ActivityInfo activityInfo);

    /**
     * 查询审核信息列表
     *
     * @param activityInfo 审核信息
     * @return 审核信息集合
     */
    List<ActivityInfo> selectActivityProcessList(ActivityInfo activityInfo);

    List<ActivityInfo> getPublishActivities(ActivityInfo activityInfo);

    List<ActivityInfo> getParticipatedActivities(ActivitySearchVo activitySearchVo);

    List<ActivityInfo> getAuditActivityList(@Param("activityStatus") String activityStatus, @Param("createBy") String createBy);

}
