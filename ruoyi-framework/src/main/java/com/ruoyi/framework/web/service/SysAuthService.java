package com.ruoyi.framework.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.SocialRegisterBody;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.AuthUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.enums.SocialLoginStatus;
import com.ruoyi.system.domain.SysAuthUser;
import com.ruoyi.system.domain.vo.SocialLoginVo;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import me.zhyd.oauth.cache.AuthDefaultStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Service
public class SysAuthService {
    private static final Logger log = LoggerFactory.getLogger(SysAuthService.class);
    private AuthStateCache authStateCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private ISysUserService sysUserService;

    {
        authStateCache = AuthDefaultStateCache.INSTANCE;
    }

    public AjaxResult authBinding(String source, HttpServletRequest request) {
        LoginUser tokenUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(tokenUser) && sysUserService.checkAuthUser(tokenUser.getUserId(), source) > 0) {
            return AjaxResult.error( MessageUtils.message("{platform.account.already.bound}"));
        }
        String clientIdKey = StringUtils.format("auths.{}.clientId", source);
        String clientSecretKey = StringUtils.format("auths.{}.clientSecret", source);
        String redirectUriKey = StringUtils.format("auths.{}.redirectUri", source);

        String clientId = sysConfigService.selectConfigByKey(clientIdKey);
        String clientSecret = sysConfigService.selectConfigByKey(clientSecretKey);
        String redirectUri = sysConfigService.selectConfigByKey(redirectUriKey);
        if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(clientSecret) || StringUtils.isEmpty(redirectUri)) {
            return AjaxResult.error(10002, MessageUtils.message("{third.party.platform.not.supported.or.no.source}"));
        }
        AuthRequest authRequest = AuthUtils.getAuthRequest(source, clientId, clientSecret, redirectUri, authStateCache);
        String authorizeUrl = null;
        try {
            authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
            authorizeUrl = authorizeUrl.replaceAll("[\\p{Cntrl}]", "");
            log.info("authorizeUrl:" + authorizeUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return AjaxResult.success(authorizeUrl);
    }

    /**
     * 第三方社交平台登录
     * @param source 平台类型
     * @param callback 回调函数
     * @param request 传参
     * @return
     */
//    public AjaxResult socialLogin( String source, AuthCallback callback, HttpServletRequest request){
//        String clientIdKey = StringUtils.format("auths.{}.clientId",source);
//        String clientSecretKey = StringUtils.format("auths.{}.clientSecret",source);
//        String redirectUriKey = StringUtils.format("auths.{}.redirectUri",source);
//        String clientId = sysConfigService.selectConfigByKey(clientIdKey);
//        String clientSecret = sysConfigService.selectConfigByKey(clientSecretKey);
//        String redirectUri = sysConfigService.selectConfigByKey(redirectUriKey);
//        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)||StringUtils.isEmpty(redirectUri)){
//            return AjaxResult.error(10002, "第三方平台系统不支持或未提供来源");
//        }
//        AuthRequest authRequest = AuthUtils.getAuthRequest(source, clientId, clientSecret, redirectUri, authStateCache);
//        AuthResponse<AuthUser> response = authRequest.login(callback);
//        if (response.ok()) {
//            LoginUser tokenUser = tokenService.getLoginUser(request);
//            if (StringUtils.isNotNull(tokenUser)) {
//                SysUser user = sysUserService.selectAuthUserByUuid(source + response.getData().getUuid());
//                if (StringUtils.isNotNull(user)) {
//                    String token = tokenService.createToken(SecurityUtils.getLoginUser());
//                    return AjaxResult.success().put(Constants.TOKEN, token);
//                }
//                // 若已经登录则直接绑定系统账号
//                SysAuthUser authUser = new SysAuthUser();
//                authUser.setAvatar(response.getData().getAvatar());
//                authUser.setUuid(source + response.getData().getUuid());
//                authUser.setUserId(SecurityUtils.getUserId());
//                authUser.setUserName(response.getData().getUsername());
//                authUser.setNickName(response.getData().getNickname());
//                authUser.setEmail(response.getData().getEmail());
//                authUser.setSource(source);
//                sysUserService.insertAuthUser(authUser);
//                String token = tokenService.createToken(SecurityUtils.getLoginUser());
//                return AjaxResult.success().put(Constants.TOKEN, token);
//            }
//            SysUser authUser = sysUserService.selectAuthUserByUuid(source + response.getData().getUuid());
//            if (StringUtils.isNotNull(authUser)) {
//                SysUser user = userService.selectUserByUserName(authUser.getUserName());
//                if (StringUtils.isNull(user)) {
//                    throw new ServiceException("登录用户：" + user.getUserName() + " 不存在");
//                } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
//                    throw new ServiceException("对不起，您的账号：" + user.getUserName() + " 已被删除");
//                } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
//                    throw new ServiceException("对不起，您的账号：" + user.getUserName() + " 已停用");
//                }
//                LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
//                String token = tokenService.createToken(loginUser);
//                return AjaxResult.success().put(Constants.TOKEN, token);
//            } else {
//                AuthUser socialUser = response.getData();
//                log.info("socialUser:"+ JSON.toJSONString(socialUser));
//                String uuid = socialUser.getUuid();
//                String avatar = socialUser.getAvatar();
//                String email = socialUser.getEmail();
//                String username = socialUser.getUsername();
//                String nickname = "";
//                String familyName=null;
//                String givenName =null;
//                JSONObject rawUserInfo = socialUser.getRawUserInfo();
//                familyName = rawUserInfo.getString("family_name");
//                givenName = rawUserInfo.getString("given_name");
//                nickname = String.format("{}{}",familyName,givenName);
////                SysUser sysUser = sysUserService.checkSocialUser(familyName, givenName, email);
//
//                String password = sysConfigService.selectConfigByKey("sys.user.initPassword");
//                SysUser sysUser = new SysUser();
//                sysUser.setEmail(email);
//                sysUser.setPassword(SecurityUtils.encryptPassword(password));
//                sysUser.setDurationOfQualify(0L);
//                sysUser.setAvatar(avatar);
//                sysUser.setFamilyName(familyName);
//                sysUser.setGivenName(givenName);
//                sysUser.setNickName(nickname);
//                sysUser.setUserName(nickname);
//                // 默认部门
//                Long[] postIds = {4L};
//                // 默认角色
//                Long[] roleIds = {2L};
//                sysUser.setPostIds(postIds);
//                sysUser.setRoleIds(roleIds);
//                sysUser.setDeptId(100L);
//                SysUser sysUser1 = sysUserService.registerUser(sysUser);
//                SysAuthUser authUser1 = new SysAuthUser();
//                authUser1.setAvatar(avatar);
//                authUser1.setUuid(source + uuid);
//                authUser1.setUserId(sysUser1.getUserId());
//                authUser1.setUserName(username);
//                authUser1.setNickName(nickname);
//                authUser1.setEmail(email);
//                authUser1.setSource(source);
//                sysUserService.insertAuthUser(authUser1);
//                LoginUser loginUser = new LoginUser(sysUser1.getUserId(), sysUser1.getDeptId(), sysUser1, permissionService.getMenuPermission(sysUser1));
//                String token = tokenService.createToken(loginUser);
//                return AjaxResult.success().put(Constants.TOKEN, token);
//            }
//        }
//        return AjaxResult.error(10002, MessageUtils.i18nMessage("{authorization.verification.failed.contact.admin}"));
//    }

    /**
     * 第三方社交平台登录
     *
     * @param source   平台类型
     * @param callback 回调函数
     * @param request  传参
     * @return
     */
    public AjaxResult socialLogin(String source, AuthCallback callback, HttpServletRequest request) {
        String clientIdKey = StringUtils.format("auths.{}.clientId", source);
        String clientSecretKey = StringUtils.format("auths.{}.clientSecret", source);
        String redirectUriKey = StringUtils.format("auths.{}.redirectUri", source);
        String clientId = sysConfigService.selectConfigByKey(clientIdKey);
        String clientSecret = sysConfigService.selectConfigByKey(clientSecretKey);
        String redirectUri = sysConfigService.selectConfigByKey(redirectUriKey);
        log.info(clientId);
        log.info(clientSecret);
        log.info(redirectUri);
        if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(clientSecret) || StringUtils.isEmpty(redirectUri)) {
            return AjaxResult.error(SocialLoginStatus.FAIL.getCode(), MessageUtils.message("{third.party.platform.not.supported.or.no.source}"));
        }
        AuthRequest authRequest = AuthUtils.getAuthRequest(source, clientId, clientSecret, redirectUri, authStateCache);
        log.info(JSON.toJSONString(callback));
        AuthResponse<AuthUser> response = authRequest.login(callback);
        int code = response.getCode();
        String msg = response.getMsg();
        log.info(String.valueOf(code));
        log.info(msg);
        if (response.ok()) {
            LoginUser tokenUser = tokenService.getLoginUser(request);
            String uuid = source + response.getData().getUuid();
            if (StringUtils.isNotNull(tokenUser)) {
                SysUser user = sysUserService.selectAuthUserByUuid(uuid);
                if (StringUtils.isNotNull(user)) {
                    String token = tokenService.createToken(SecurityUtils.getLoginUser());
                    return AjaxResult.success().put(Constants.TOKEN, token);
                }
                // 若已经登录则直接绑定系统账号
                SysAuthUser authUser = new SysAuthUser();
                authUser.setAvatar(response.getData().getAvatar());
                authUser.setUuid(source + response.getData().getUuid());
                authUser.setUserId(SecurityUtils.getUserId());
                authUser.setUserName(response.getData().getUsername());
                authUser.setNickName(response.getData().getNickname());
                authUser.setEmail(response.getData().getEmail());
                authUser.setSource(source);
                sysUserService.insertAuthUser(authUser);
                String token = tokenService.createToken(SecurityUtils.getLoginUser());
                return AjaxResult.success().put(Constants.TOKEN, token);
            }
            SysUser authUser = sysUserService.selectAuthUserByUuid(uuid);
            if (StringUtils.isNotNull(authUser)) {
                SysUser user = userService.selectUserByUserName(authUser.getUserName());
                if (StringUtils.isNull(user)) {
                    throw new ServiceException("{user.not.found}");
                } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
                    throw new ServiceException("{account.deleted}");
                } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
                    throw new ServiceException("{account.suspended}");
                }
                LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
                String token = tokenService.createToken(loginUser);
                return AjaxResult.success().put(Constants.TOKEN, token);
            } else {
                AuthUser socialUser = response.getData();
                String email = socialUser.getEmail();
                String avatar = socialUser.getAvatar();
                String username = socialUser.getUsername();
                String nickname = socialUser.getNickname();
                JSONObject rawUserInfo = socialUser.getRawUserInfo();
                String familyName = rawUserInfo.getString("family_name");
                String givenName = rawUserInfo.getString("given_name");
                SysUser sysUser = sysUserService.checkSocialUser(familyName, givenName, email);
                SocialLoginVo socialLoginVo = new SocialLoginVo();
                socialLoginVo.setFamilyName(familyName);
                socialLoginVo.setGivenName(givenName);
                socialLoginVo.setEmail(email);
                socialLoginVo.setSource(source);
                socialLoginVo.setAvatar(avatar);
                socialLoginVo.setNickName(nickname);
                socialLoginVo.setUserName(username);
                int socialLoginStatus=StringUtils.isNotNull(sysUser)?SocialLoginStatus.ACCOUNT_BOUND.getCode():SocialLoginStatus.UPDATE_PROFILE.getCode();
                if (StringUtils.isNotNull(sysUser)) {
                    socialLoginVo.setAccountCheck(true);
                    socialLoginVo.setUserId(sysUser.getUserId());
                } else {
                    socialLoginVo.setAccountCheck(false);
                }
                socialLoginVo.setUuid(uuid);
                return AjaxResult.success(socialLoginStatus,socialLoginVo);
            }
        }
        return AjaxResult.error(SocialLoginStatus.FAIL.getCode(), MessageUtils.message("{authorization.verification.failed.contact.admin}"));
    }


    /**
     * 取消授权
     */
    public int unlockAuth(@PathVariable Long authId) {
        return sysUserService.deleteAuthUser(authId);
    }


    public AjaxResult socialLock(SocialLoginVo socialLoginVo) {
        String uuid = socialLoginVo.getUuid();
        String userName = socialLoginVo.getUserName();
        String source = socialLoginVo.getSource();
        String nickName = socialLoginVo.getNickName();
        String email = socialLoginVo.getEmail();
        String avatar = socialLoginVo.getAvatar();
        Long userId = socialLoginVo.getUserId();
        SysAuthUser authUser = new SysAuthUser();
        authUser.setAvatar(avatar);
        authUser.setUuid(uuid);
        authUser.setUserId(userId);
        authUser.setUserName(userName);
        authUser.setNickName(nickName);
        authUser.setEmail(email);
        authUser.setSource(source);
        sysUserService.insertAuthUser(authUser);
        SysUser sysUser = sysUserService.selectUserById(userId);
        LoginUser loginUser = new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, permissionService.getMenuPermission(sysUser));
        String token = tokenService.createToken(loginUser);
        return AjaxResult.success().put(Constants.TOKEN, token);
    }

    /**
     * 第三方补全信息
     *
     * @param socialRegisterBody
     * @return
     */
    public AjaxResult socialRegister(SocialRegisterBody socialRegisterBody) {
        String uuid = socialRegisterBody.getUuid();
        String graduationYear = socialRegisterBody.getGraduationYear();
        String password = socialRegisterBody.getPassword();
        String internalEmail = socialRegisterBody.getInsideEmail();
        String email = socialRegisterBody.getEmail();
        String studentId = socialRegisterBody.getStudentId();
        String source = socialRegisterBody.getSource();
        String username = socialRegisterBody.getUsername();
        Long counselorId = socialRegisterBody.getCounselorId();
        String familyName = socialRegisterBody.getFamilyName();
        String givenName = socialRegisterBody.getGivenName();
        String avatar = socialRegisterBody.getAvatar();
        SysUser sysUser = new SysUser();
        sysUser.setGivenName(givenName);
        sysUser.setFamilyName(familyName);
        sysUser.setExternalEmail(email);
        sysUser.setInternalEmail(internalEmail);
        sysUser.setUserName(username);
        String nickname = StringUtils.format("{}{}", familyName, givenName);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        sysUser.setDurationOfQualify(0L);
        sysUser.setCounselorId(counselorId);
        sysUser.setAvatar(avatar);
        sysUser.setGraduationYear(graduationYear);
        if (!userService.checkEmailUnique(sysUser)) {
            throw new ServiceException("{user.save.failed.email.exists}");
        } else if (!userService.checkUserNameUnique(sysUser)) {
            throw new ServiceException("{user.save.failed.account.exists}");
        } else {
            // 默认部门
            Long[] postIds = {4L};
            // 默认角色
            Long[] roleIds = {2L};
            sysUser.setPostIds(postIds);
            sysUser.setRoleIds(roleIds);
            sysUser.setDeptId(100L);
            SysUser sysUser1 = sysUserService.registerUser(sysUser);
            Long userId = sysUser1.getUserId();
            SysAuthUser authUser1 = new SysAuthUser();
            authUser1.setUserId(userId);
            authUser1.setAvatar(avatar);
            authUser1.setUuid(uuid);
            authUser1.setUserId(sysUser1.getUserId());
            authUser1.setUserName(username);
            authUser1.setNickName(nickname);
            authUser1.setEmail(email);
            authUser1.setSource(source);
            sysUserService.insertAuthUser(authUser1);
            LoginUser loginUser = new LoginUser(sysUser1.getUserId(), sysUser1.getDeptId(), sysUser1, permissionService.getMenuPermission(sysUser1));
            String token = tokenService.createToken(loginUser);
            return AjaxResult.success().put(Constants.TOKEN, token);
        }
    }
}
