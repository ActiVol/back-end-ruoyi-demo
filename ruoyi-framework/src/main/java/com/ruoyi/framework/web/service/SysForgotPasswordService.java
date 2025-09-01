package com.ruoyi.framework.web.service;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ForgotPasswordBody;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EmailUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 忘记密码校验方法
 *
 * @author ruoyi
 */
@Component
public class SysForgotPasswordService {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private EmailUtils emailUtils;
    private static final String KEY_PREFIX = "RESET_PASSWORD";


    public void buildContent(SysUser sysUser){
        String email = sysUser.getExternalEmail();
        String emailTemp = configService.selectConfigByKey("sys.forgotpassword.emailTemplate");
        String userName = sysUser.getUserName();
        String uuid = IdUtils.simpleUUID();
        String date = DateUtils.getDate();
        String content = StringUtils.format(emailTemp, userName, uuid, date);
        String subject = "Volunteer 重置密码";
        int  expireTime = 1000*60*60*48;
        String key = KEY_PREFIX+uuid;
        redisCache.setCacheObject(key,sysUser,expireTime,TimeUnit.MINUTES);
        emailUtils.sendMessage(email,subject,content);

    }


    public String resetPasswordByUrl(LoginBody loginBody) {
        String msg = "" ,
            password = loginBody.getPassword(),
            uuid = loginBody.getUuid();
        if(StringUtils.isEmpty(uuid)){
            msg = "验证规则失败！，code为空！";
        }
        if(StringUtils.isEmpty(password)){
            msg = "验证规则失败！，密码为空！";
        }
        String key = KEY_PREFIX+uuid;
        SysUser sysUser = redisCache.getCacheObject(key);
        if(sysUser==null){
            msg = "code已过期，请重新进行邮件申请";
        }else{
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            userService.resetPwd(sysUser);
        }
        return msg;
    }
}
