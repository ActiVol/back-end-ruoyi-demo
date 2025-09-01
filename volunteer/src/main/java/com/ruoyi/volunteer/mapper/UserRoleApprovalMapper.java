package com.ruoyi.volunteer.mapper;

import java.util.List;
import com.ruoyi.volunteer.domain.UserRoleApproval;

/**
 * 用户角色审核Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-16
 */
public interface UserRoleApprovalMapper 
{
    /**
     * 查询用户角色审核
     * 
     * @param id 用户角色审核主键
     * @return 用户角色审核
     */
    public UserRoleApproval selectUserRoleApprovalById(Long id);

    /**
     * 查询用户角色审核列表
     * 
     * @param userRoleApproval 用户角色审核
     * @return 用户角色审核集合
     */
    public List<UserRoleApproval> selectUserRoleApprovalList(UserRoleApproval userRoleApproval);

    /**
     * 新增用户角色审核
     * 
     * @param userRoleApproval 用户角色审核
     * @return 结果
     */
    public int insertUserRoleApproval(UserRoleApproval userRoleApproval);

    /**
     * 修改用户角色审核
     * 
     * @param userRoleApproval 用户角色审核
     * @return 结果
     */
    public int updateUserRoleApproval(UserRoleApproval userRoleApproval);

    /**
     * 删除用户角色审核
     * 
     * @param id 用户角色审核主键
     * @return 结果
     */
    public int deleteUserRoleApprovalById(Long id);

    /**
     * 批量删除用户角色审核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserRoleApprovalByIds(Long[] ids);
}
