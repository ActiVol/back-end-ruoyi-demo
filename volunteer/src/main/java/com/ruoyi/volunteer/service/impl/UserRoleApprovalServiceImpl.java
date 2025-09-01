package com.ruoyi.volunteer.service.impl;

import java.util.List;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.UserRoleApprovalMapper;
import com.ruoyi.volunteer.domain.UserRoleApproval;
import com.ruoyi.volunteer.service.IUserRoleApprovalService;

/**
 * 用户角色审核Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-16
 */
@Service
public class UserRoleApprovalServiceImpl implements IUserRoleApprovalService {
    @Autowired
    private UserRoleApprovalMapper userRoleApprovalMapper;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService userService;


    /**
     * 查询用户角色审核
     *
     * @param id 用户角色审核主键
     * @return 用户角色审核
     */
    @Override
    public UserRoleApproval selectUserRoleApprovalById(Long id) {
        return userRoleApprovalMapper.selectUserRoleApprovalById(id);
    }

    /**
     * 查询用户角色审核列表
     *
     * @param userRoleApproval 用户角色审核
     * @return 用户角色审核
     */
    @Override
    public List<UserRoleApproval> selectUserRoleApprovalList(UserRoleApproval userRoleApproval) {
        return userRoleApprovalMapper.selectUserRoleApprovalList(userRoleApproval);
    }


    /**
     * 修改用户角色审核
     *
     * @param userRoleApproval 用户角色审核
     * @return 结果
     */
    @Override
    public int approval(UserRoleApproval userRoleApproval) {
        Long approvalStatus = userRoleApproval.getApprovalStatus();
        if(approvalStatus == 1L){
            Long[] roleIds = {userRoleApproval.getRoleId()};
            userService.insertUserAuth(userRoleApproval.getUserId(),roleIds);
        }else if(approvalStatus == 2L){
            Long[] roleIds = {2L};
            userService.insertUserAuth(userRoleApproval.getUserId(),roleIds);
        }
        userRoleApproval.setApprovedBy(SecurityUtils.getUsername());
        userRoleApproval.setApprovalTime(DateUtils.getNowDate());
        userRoleApproval.setApprovedUserId(SecurityUtils.getUserId());
        return userRoleApprovalMapper.updateUserRoleApproval(userRoleApproval);
    }


    @Override
    public String H5registerUser(RegisterBody registerBody) {
        String msg = "",
                username = registerBody.getUsername(),
                password = registerBody.getPassword(),
                graduationYear = registerBody.getGraduationYear(),
                internalEmail = registerBody.getInternalEmail(),
                middleName = registerBody.getMiddleName(),
                userType = registerBody.getUserType(),
                externalEmail = registerBody.getExternalEmail();
        String familyName = registerBody.getFamilyName();
        String givenName = registerBody.getGivenName();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setExternalEmail(externalEmail);
        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(registerBody.getCode(), registerBody.getUuid());
        }
        if (!userService.checkEmailUnique(sysUser)) {
            msg = "保存用户'" + externalEmail + "'失败，注册邮箱已存在";
        } else if (!userService.checkUserNameUnique(sysUser)) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            // 默认部门
            Long[] postIds = {4L};
            // 默认角色
            Long[] roleIds = {2L};
            sysUser.setPostIds(postIds);
            if (StringUtils.isNotEmpty(graduationYear)) {
                sysUser.setGraduationYear(graduationYear);
            }
            if (StringUtils.isNotEmpty(internalEmail)) {
                sysUser.setInternalEmail(internalEmail);
            }
            if (StringUtils.isNotEmpty(externalEmail)) {
                sysUser.setExternalEmail(externalEmail);
            }
            if (StringUtils.isNotEmpty(familyName)) {
                sysUser.setFamilyName(familyName);
            }
            if (StringUtils.isNotEmpty(givenName)) {
                sysUser.setGivenName(givenName);
            }
            sysUser.setRoleIds(roleIds);
            sysUser.setDeptId(100L);
            sysUser.setMiddleName(middleName);
            SysUser sysUser1 = userService.registerUser(sysUser);
            if (sysUser1 == null || sysUser1.getUserId() == null || sysUser1.getUserId() == 0L) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                if (!"Student".equals(userType)) {
                    Long ruleId = "Counselor".equals(userType) ? 3L : 4L;
                    UserRoleApproval userRoleApproval = new UserRoleApproval();
                    userRoleApproval.setApprovalStatus(0L);
                    userRoleApproval.setUserName(sysUser1.getUserName());
                    userRoleApproval.setUserId(sysUser1.getUserId());
                    userRoleApproval.setRoleId(ruleId);
                    userRoleApproval.setRuleName(userType);
                    userRoleApprovalMapper.insertUserRoleApproval(userRoleApproval);
                }
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
