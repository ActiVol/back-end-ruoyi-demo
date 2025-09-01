package com.ruoyi.volunteer.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.volunteer.domain.ActivityProcess;

/**
 * 审核信息Mapper接口
 *
 * @author guyue
 * @date 2024-12-13
 */
public interface ActivityProcessMapper extends BaseMapper<ActivityProcess> {
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
     * 删除审核信息
     *
     * @param processId 审核信息主键
     * @return 结果
     */
    public int deleteActivityProcessByProcessId(Long processId);

    /**
     * 批量删除审核信息
     *
     * @param processIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityProcessByProcessIds(Long[] processIds);
}
