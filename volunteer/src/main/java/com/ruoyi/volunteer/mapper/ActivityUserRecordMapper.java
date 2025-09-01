package com.ruoyi.volunteer.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.volunteer.domain.ActivityUserRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 用户报名活动记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-07-13
 */
public interface ActivityUserRecordMapper extends BaseMapper<ActivityUserRecord>
{
    /**
     * 查询用户报名活动记录
     *
     * @param id 用户报名活动记录主键
     * @return 用户报名活动记录
     */
    public ActivityUserRecord selectActivityUserRecordById(Long id);

    /**
     * 查询用户报名活动记录列表
     *
     * @param activityUserRecord 用户报名活动记录
     * @return 用户报名活动记录集合
     */
    public List<ActivityUserRecord> selectActivityUserRecordList(ActivityUserRecord activityUserRecord);

    /**
     * 新增用户报名活动记录
     *
     * @param activityUserRecord 用户报名活动记录
     * @return 结果
     */
    public int insertActivityUserRecord(ActivityUserRecord activityUserRecord);

    /**
     * 修改用户报名活动记录
     *
     * @param activityUserRecord 用户报名活动记录
     * @return 结果
     */
    public int updateActivityUserRecord(ActivityUserRecord activityUserRecord);

    /**
     * 删除用户报名活动记录
     *
     * @param id 用户报名活动记录主键
     * @return 结果
     */
    public int deleteActivityUserRecordById(Long id);

    /**
     * 批量删除用户报名活动记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityUserRecordByIds(Long[] ids);



    // 组织者查看的列表
    List<Map<String,String>> zzzPendingReviewList(@Param("zzzUserId") String zzzUserId);

    // 查看用户在某个活动中是否有报名记录
    Integer selectUserInActivityRecord(@Param("userId") String userId,@Param("activityId") String activityId);



 }
