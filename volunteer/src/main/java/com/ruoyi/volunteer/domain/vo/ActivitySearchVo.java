package com.ruoyi.volunteer.domain.vo;

import com.ruoyi.common.xss.Xss;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ActivitySearchVo {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户名")
    private String username;

    @Xss(message = "{activitySarch.email.xss}")
    @NotBlank(message = "{activitySarch.email.notBlank}")
    @Size(min = 0, max = 50, message = "{activitySarch.email.size}")
    @Email(message = "{activitySarch.email}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Xss(message = "{activitySarch.username.notBlank}")
    @NotBlank(message = "{activitySarch.username.xss}")
    @Size(min = 0, max = 30, message = "{activitySarch.username.size}")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
