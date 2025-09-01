package com.ruoyi.web.controller.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.ruoyi.common.core.domain.model.SocialRegisterBody;
import com.ruoyi.framework.web.service.SysAuthService;
import com.ruoyi.system.domain.vo.SocialLoginVo;
import com.ruoyi.system.mapper.SysConfigMapper;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.AuthUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.SysAuthUser;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import me.zhyd.oauth.cache.AuthDefaultStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

/**
 * 第三方认证授权处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/auth")
public class SysAuthController extends BaseController {
    @Autowired
    private SysAuthService sysAuthService;
    /**
     * 认证授权
     *
     * @param source
     * @throws IOException
     */
    @GetMapping("/binding/{source}")
    @ResponseBody
    public AjaxResult authBinding(@PathVariable("source") String source, HttpServletRequest request) throws IOException {
        return sysAuthService.authBinding(source,request);
    }
    /**
     * 第三方社交平台登录
     * @param source 平台类型
     * @param callback 回调函数
     * @param request 传参
     * @return
     */
    @GetMapping("/socialLogin/{source}")
    public AjaxResult socialLogin(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request) {
        return sysAuthService.socialLogin(source,callback,request);
    }

    /**
     * 取消授权
     */
    @DeleteMapping(value = "/unlock/{authId}")
    public AjaxResult unlockAuth(@PathVariable Long authId) {
        return toAjax(sysAuthService.unlockAuth(authId));
    }
    /**
     * 用户绑定第三方登录
     */
    @PostMapping(value = "/socialLock")
    public AjaxResult unlockAuth(@RequestBody SocialLoginVo socialLoginVo) {
        AjaxResult ajaxResult = sysAuthService.socialLock(socialLoginVo);
        return ajaxResult;
    }

    /**
     * 第三方补全信息
     * @param socialRegisterBody
     * @return
     */
    @PostMapping(value = "/socialRegister")
    public AjaxResult socialRegister(@Validated @RequestBody SocialRegisterBody socialRegisterBody) {
        AjaxResult ajaxResult = sysAuthService.socialRegister(socialRegisterBody);
        return ajaxResult;
    }
}
