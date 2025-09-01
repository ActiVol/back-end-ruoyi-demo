package com.ruoyi.volunteer.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 临时活动对象 temp_activities
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public class TempActivities extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "临时活动ID")
    private Long id;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    @Excel(name = "用户账号")
    private String userName;

    /**
     * 用户邮箱
     */
    @Excel(name = "外部邮箱")
    @ApiModelProperty(value = "外部邮箱")
    private String externalEmail;

    /**
     * 内部邮箱
     */
    @Excel(name = "内部邮箱")
    @ApiModelProperty(value = "内部邮箱")
    private String internalEmail;


    /**
     * 毕业年份
     */
    @Excel(name = "毕业年份")
    @ApiModelProperty(value = "毕业年份")
    private String graduationYear;


    /**
     * 活动名称
     */
    @Excel(name = "活动名称")
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 活动地址
     */
    @Excel(name = "活动地址")
    @ApiModelProperty(value = "活动地址")
    private String activityAddress;

    /**
     * 活动详情
     */
    @Excel(name = "活动详情")
    @ApiModelProperty(value = "活动详情")
    private String activityDetails;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date activityStartTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date activityEndTime;

    /**
     * 活动时长
     */
    @Excel(name = "活动时长")
    @ApiModelProperty(value = "活动时长")
    private Long activityDuration;

    /**
     * 举办者名称
     */
    @Excel(name = "举办者名称")
    @ApiModelProperty(value = "举办者名称")
    private String organizerName;

    /**
     * 举办者邮件
     */
    @Excel(name = "举办者邮件")
    @ApiModelProperty(value = "举办者邮件")
    private String organizerEmail;

    /**
     * 对管理员的留言
     */
    @Excel(name = "对管理员的留言")
    @ApiModelProperty(value = "对管理员的留言")
    private String commentToAdmin;

    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty(value = "状态")
    private String reviewStatus;

    /**
     * 招募人数
     */
    @ApiModelProperty(value = "招募人数")
    private Integer recruitNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExternalEmail() {
        return externalEmail;
    }

    public void setExternalEmail(String externalEmail) {
        this.externalEmail = externalEmail;
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(String activityDetails) {
        this.activityDetails = activityDetails;
    }

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public Long getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(Long activityDuration) {
        this.activityDuration = activityDuration;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getCommentToAdmin() {
        return commentToAdmin;
    }

    public void setCommentToAdmin(String commentToAdmin) {
        this.commentToAdmin = commentToAdmin;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(Integer recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

}
