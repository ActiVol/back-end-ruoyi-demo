package com.ruoyi.volunteer.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.volunteer.domain.ActivityUserRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 用户报名活动记录Service接口
 *
 * @author ruoyi
 * @date 2025-07-13
 */
public interface IActivityUserRecordService
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
     * 批量删除用户报名活动记录
     *
     * @param ids 需要删除的用户报名活动记录主键集合
     * @return 结果
     */
    public int deleteActivityUserRecordByIds(Long[] ids);

    /**
     * 删除用户报名活动记录信息
     *
     * @param id 用户报名活动记录主键
     * @return 结果
     */
    public int deleteActivityUserRecordById(Long id);

    ActivityUserRecord selectSignUpInfo(String activityId,String userId);

    AjaxResult signUpPass(String activityId,String userId);

    AjaxResult signUpReject(String activityId,String userId);

    AjaxResult signUpCancel(String activityId,String userId);

    List<Map<String,String>> zzzPendingReviewList( );

}
