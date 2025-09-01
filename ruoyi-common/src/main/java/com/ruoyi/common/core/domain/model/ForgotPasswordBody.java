package com.ruoyi.common.core.domain.model;

public class ForgotPasswordBody {
    /**
     * 验证码
     */
    private String code;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 唯一标识
     */
    private String uuid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
