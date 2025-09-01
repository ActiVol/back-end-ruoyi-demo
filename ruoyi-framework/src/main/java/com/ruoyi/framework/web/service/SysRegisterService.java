package com.ruoyi.framework.web.service;

import com.ruoyi.common.core.domain.entity.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
public class SysRegisterService {
    @Autowired
    private ISysUserService userService;


    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "",
            username = registerBody.getUsername(),
            password = registerBody.getPassword(),
                graduationYear = registerBody.getGraduationYear(),
                internalEmail = registerBody.getInternalEmail(),
                middleName = registerBody.getMiddleName(),
                userType = registerBody.getUserType(),
                email = registerBody.getExternalEmail();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(registerBody.getCode(), registerBody.getUuid());
        }
        if(!userService.checkEmailUnique(sysUser)){
            msg = "保存用户'" + email + "'失败，注册邮箱已存在";
        } else if (!userService.checkUserNameUnique(sysUser)) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            // 默认部门
            Long[] postIds = {4L};
            // 默认角色
            Long[] roleIds = {2L};
            sysUser.setPostIds(postIds);
            sysUser.setRoleIds(roleIds);
            sysUser.setDeptId(100L);
            sysUser.setMiddleName(middleName);
            SysUser sysUser1 = userService.registerUser(sysUser);
            if (sysUser1==null||sysUser1.getUserId()==null||sysUser1.getUserId()==0L) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }
    /**
     * 校验验证码
     *
     * @param code     验证码
     * @param uuid     唯一标识
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
