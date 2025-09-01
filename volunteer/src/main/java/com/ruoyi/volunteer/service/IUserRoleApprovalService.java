package com.ruoyi.volunteer.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.volunteer.domain.UserRoleApproval;

/**
 * 用户角色审核Service接口
 * 
 * @author ruoyi
 * @date 2025-03-16
 */
public interface IUserRoleApprovalService 
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
     * 修改用户角色审核
     * 
     * @param userRoleApproval 用户角色审核
     * @return 结果
     */
    public int approval(UserRoleApproval userRoleApproval);


    /**
     * H5注册用户信息
     *
     * @param user     用户信息
     * @return 结果
     */
    public String H5registerUser(RegisterBody user);
}
