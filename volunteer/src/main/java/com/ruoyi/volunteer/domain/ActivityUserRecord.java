package com.ruoyi.volunteer.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户报名活动记录对象 activity_user_record
 *
 * @author ruoyi
 * @date 2025-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString@TableName(value = "activity_user_record")
public class ActivityUserRecord
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 活动关联的用户ID */
    @Excel(name = "活动关联的用户ID")
    private Long userId;

    /** 活动关联的用户名 */
    @Excel(name = "活动关联的用户名")
    private String userName;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 报名时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报名时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date signupTime;

    /** 参与状态 (0-已报名，1-已参与，2-已取消) */
    @Excel(name = "参与状态 (0-已报名，1-已参与，2-已取消)")
    private Long participationStatus;

    /** 参与时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "参与时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date participationDate;

    /** 组织者审核人 */
    @Excel(name = "组织者审核人")
    private String reviewOrganizer;

    /** 组织者审核状态 (0-待审核，1-通过，2-驳回) */
    @Excel(name = "组织者审核状态 (0-待审核，1-通过，2-驳回)")
    private Long reviewOrganizerStatus;

    /** 组织者审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "组织者审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reviewOrganizerTime;

    /** 管理员审核人 */
    @Excel(name = "管理员审核人")
    private String reviewAdmin;

    /** 管理员审核状态 (0-待审核，1-通过，2-驳回) */
    @Excel(name = "管理员审核状态 (0-待审核，1-通过，2-驳回)")
    private Long reviewAdminStatus;

    /** 管理员审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "管理员审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reviewAdminTime;



}
