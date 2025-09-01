package com.ruoyi.volunteer.service;

import java.util.List;

import com.ruoyi.volunteer.domain.TempActivities;

/**
 * 临时活动Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ITempActivitiesService {
    /**
     * 查询临时活动
     *
     * @param id 临时活动主键
     * @return 临时活动
     */
    public TempActivities selectTempActivitiesById(Long id);

    /**
     * 查询临时活动列表
     *
     * @param tempActivities 临时活动
     * @return 临时活动集合
     */
    public List<TempActivities> selectTempActivitiesList(TempActivities tempActivities);

    /**
     * 新增临时活动
     *
     * @param tempActivities 临时活动
     * @return 结果
     */
    public int insertTempActivities(TempActivities tempActivities);

    /**
     * 修改临时活动
     *
     * @param tempActivities 临时活动
     * @return 结果
     */
    public int updateTempActivities(TempActivities tempActivities);

    /**
     * 批量删除临时活动
     *
     * @param ids 需要删除的临时活动主键集合
     * @return 结果
     */
    public int deleteTempActivitiesByIds(Long[] ids);

    /**
     * 删除临时活动信息
     *
     * @param id 临时活动主键
     * @return 结果
     */
    public int deleteTempActivitiesById(Long id);

    Boolean processTempActivity(Long id);

}
