package com.ruoyi.volunteer.controller;


import com.github.pagehelper.PageInfo;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ForgotPasswordBody;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.SysForgotPasswordService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.service.IActivityInfoService;
import com.ruoyi.volunteer.service.IFrontService;
import com.ruoyi.volunteer.service.IUserRoleApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@RequestMapping(value = "/open/front")
public class FrontController {

    @Autowired
    private IFrontService frontService;
    @Autowired
    private IActivityInfoService activityInfoService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserRoleApprovalService userRoleApprovalService;
    @Autowired
    private ISysUserService userService;


    @GetMapping(value = "/viewState")
    public AjaxResult viewState() {
        Map<String, Object> stringObjectMap = frontService.viewState();
        return AjaxResult.success(stringObjectMap);
    }

    // Activity Details
    @GetMapping(value = "/activityDetails")
    public AjaxResult activityDetails() {
        return AjaxResult.success(frontService.activityDetails());
    }

    @GetMapping(value = "/publishActivitys")
    public TableDataInfo publishActivitys() {
        startPage();
        List<ActivityInfo> activityInfos = frontService.publishActivities();
        return getDataTable(activityInfos);

    }


    @GetMapping(value = "/getActivityDetailById/{activityId}")
    public AjaxResult getActivityDetailById(@PathVariable(name = "activityId", required = true) Long activityId  ) {
        ActivityInfo activityInfo = activityInfoService.selectActivityInfoByActivityId(activityId);
        activityInfo.setReviewLog(new ArrayList<>());
        return AjaxResult.success(activityInfo);
    }

    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }




    @PostMapping("/register")
    public AjaxResult register(@Validated @RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return AjaxResult.error("当前系统没有开启注册功能！");
        }
        String msg = registerUser(user);
        return StringUtils.isEmpty(msg) ? AjaxResult.success() : AjaxResult.error(msg);
    }



    public String registerUser(RegisterBody registerBody) {
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
            sysUser.setFamilyName(registerBody.getFamilyName());
            sysUser.setGivenName(registerBody.getGivenName());
            sysUser.setGraduationYear(graduationYear);
            sysUser.setExternalEmail(email);
            sysUser.setInternalEmail(internalEmail);

            SysUser sysUser1 = userService.registerUser(sysUser);
            if (sysUser1==null||sysUser1.getUserId()==null||sysUser1.getUserId()==0L) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    @Autowired
    private RedisCache redisCache;
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
