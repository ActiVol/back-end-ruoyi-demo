package com.ruoyi.common.core.domain.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SocialRegisterBody {
    /*学号*/
    private String studentId;
    /*毕业年份*/
    private String graduationYear;
    /*校内邮箱*/
    private String internalEmail;
    /*校外邮箱*/
    private String email;
    /**
     * 头像信息
     */
    private String avatar;
    /**
     * 第三方类型
     */
    private String source;
    /**
     * 用户名
     */
    private String username;
    /**
     * 辅导员
     */
    private Long counselorId;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 姓
     */
    private String familyName;
    /**
     * 名
     */
    private String givenName;

    /**
     * 唯一标识
     */
    private String uuid;
    @NotBlank(message = "{socialRegisterBody.studentId.notblank}")
    @Size(min = 0, max = 20, message = "{socialRegisterBody.studentId.size}")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    @NotBlank(message = "{socialRegisterBody.graduationYear.notBlank}")
    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }
    @NotBlank(message = "{socialRegisterBody.internalEmail.notBlank}")
    @Email(message = "{socialRegisterBody.internalEmail.Email}")
    public String getInsideEmail() {
        return internalEmail;
    }

    public void setInsideEmail(String internalEmail) {
        this.internalEmail = internalEmail;
    }
    @NotBlank(message = "{socialRegisterBody.email.notBlank}")
    @Email(message = "{socialRegisterBody.email.Email}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @NotBlank(message = "{socialRegisterBody.userName.notblank}")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @NotBlank(message = "{socialRegisterBody.password.notblank}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotBlank(message = "{socialRegisterBody.uuid.notblank}")
    public String getUuid() {
        return uuid;
    }
    public Long getCounselorId() {
        return counselorId;
    }
    @NotBlank(message = "{socialRegisterBody.familyName.notblank}")
    @Size(min = 0, max = 50, message = "{socialRegisterBody.familyName.size}")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    @NotBlank(message = "{socialRegisterBody.givenName.notblank}")
    @Size(min = 0, max = 50, message = "{socialRegisterBody.givenName.size}")
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
