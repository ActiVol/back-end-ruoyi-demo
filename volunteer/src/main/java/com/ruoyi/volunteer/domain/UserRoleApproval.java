package com.ruoyi.volunteer.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户角色审核对象 user_role_approval
 * 
 * @author ruoyi
 * @date 2025-03-16
 */
public class UserRoleApproval extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** 用户ID */
    @ApiModelProperty(value = "${comment}")
    private Long userId;

    /** 角色ID */
    @ApiModelProperty(value = "${comment}")
    private Long roleId;

    /** 用户名 */
    @Excel(name = "用户名")
    @ApiModelProperty(value = "用户名")
    private String userName;

    /** 角色名称 */
    @Excel(name = "角色名称")
    @ApiModelProperty(value = "角色名称")
    private String ruleName;

    /** 审核状态0审核中1同意2驳回 */
    @Excel(name = "审核状态0审核中1同意2驳回")
    @ApiModelProperty(value = "审核状态0审核中1同意2驳回")
    private Long approvalStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核时间")
    private Date approvalTime;

    /** 审核人员 */
    @ApiModelProperty(value = "审核时间")
    private String approvedBy;

    /** 审核人员ID */
    @ApiModelProperty(value = "审核时间")
    private Long approvedUserId;

    /** 审核理由 */
    @Excel(name = "审核理由")
    @ApiModelProperty(value = "审核理由")
    private String reason;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRoleId(Long roleId) 
    {
        this.roleId = roleId;
    }

    public Long getRoleId() 
    {
        return roleId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setRuleName(String ruleName) 
    {
        this.ruleName = ruleName;
    }

    public String getRuleName() 
    {
        return ruleName;
    }
    public void setApprovalStatus(Long approvalStatus) 
    {
        this.approvalStatus = approvalStatus;
    }

    public Long getApprovalStatus() 
    {
        return approvalStatus;
    }
    public void setApprovalTime(Date approvalTime) 
    {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime() 
    {
        return approvalTime;
    }
    public void setApprovedBy(String approvedBy) 
    {
        this.approvedBy = approvedBy;
    }

    public String getApprovedBy() 
    {
        return approvedBy;
    }
    public void setApprovedUserId(Long approvedUserId) 
    {
        this.approvedUserId = approvedUserId;
    }

    public Long getApprovedUserId() 
    {
        return approvedUserId;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("roleId", getRoleId())
            .append("userName", getUserName())
            .append("ruleName", getRuleName())
            .append("approvalStatus", getApprovalStatus())
            .append("approvalTime", getApprovalTime())
            .append("approvedBy", getApprovedBy())
            .append("approvedUserId", getApprovedUserId())
            .append("reason", getReason())
            .toString();
    }
}
