package com.ruoyi.volunteer.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 审核信息对象 activity_process
 *
 * @author guyue
 * @date 2024-12-13
 */
@TableName(value = "activity_post_review")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActivityProcess  {
    private static final long serialVersionUID = 1L;

    /**
     * 审核ID
     */
    @ApiModelProperty(value = "审核ID")
    private Long processId;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    @TableField("reviewer")
    private String reviewer;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("review_time")
    private Date reviewTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @TableField("review_comments")
    private String reviewComments;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    @TableField("review_status")
    private String reviewStatus;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private Long activityId;



    public ActivityProcess(String reviewer, Date reviewTime, String reviewComments, String reviewStatus, Long activityId) {
        this.reviewer = reviewer;
        this.reviewTime = reviewTime;
        this.reviewComments = reviewComments;
        this.reviewStatus = reviewStatus;
        this.activityId = activityId;
    }
}
