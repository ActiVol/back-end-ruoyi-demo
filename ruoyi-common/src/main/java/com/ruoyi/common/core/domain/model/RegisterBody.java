package com.ruoyi.common.core.domain.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
public class RegisterBody extends LoginBody {

    /*毕业年份*/
    private String graduationYear;
    /*校内邮箱*/
    private String internalEmail;
    /*校外邮箱*/
    private String externalEmail;
    /**
     * 中间名
     */
    private String middleName;
    /**
     * 用户类型
     */
    private String userType;

    /**
     * 名
     */
    private String familyName;
    /**
     * 姓
     */
    private String givenName;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getInternalEmail() {
        return internalEmail;
    }

    public void setInternalEmail(String internalEmail) {
        this.internalEmail = internalEmail;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    @NotBlank(message = "{registerBody.email.notBlank}")
    @Email(message = "{registerBody.email.Email}")
    public String getExternalEmail() {
        return externalEmail;
    }

    public void setExternalEmail(String externalEmail) {
        this.externalEmail = externalEmail;
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
}
