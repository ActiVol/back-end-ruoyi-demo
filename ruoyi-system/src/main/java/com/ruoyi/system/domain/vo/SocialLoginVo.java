package com.ruoyi.system.domain.vo;

import com.ruoyi.common.core.domain.entity.SysUser;
import me.zhyd.oauth.model.AuthUser;

/**
 * 第三方登录验证返回信息
 */
public class SocialLoginVo {
    /**
     * 姓
     */
    private String familyName;
    /**
     * 名
     */
    private String givenName;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 第三方登录ID
     */
    private String uuid;
    /**
     * 用户ID
     */
    private Long userId;

    private String userName;

    private String nickName;
    private String avatar;
    private String source;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 平台是否有账号
     */
    private boolean accountCheck;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAccountCheck() {
        return accountCheck;
    }

    public void setAccountCheck(boolean accountCheck) {
        this.accountCheck = accountCheck;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
