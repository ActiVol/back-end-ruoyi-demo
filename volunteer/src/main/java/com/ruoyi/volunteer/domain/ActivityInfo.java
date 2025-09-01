package com.ruoyi.volunteer.domain;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动信息对象 activity_info
 *
 * @author guyue
 * @date 2024-12-13
 */
@TableName(value = "activity_post_info")
@Data
public class ActivityInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    @TableField("activity_id")
    private Long activityId;

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
    private String address;

    /**
     * 招募人数
     */
    @Excel(name = "招募人数")
    @ApiModelProperty(value = "招募人数")
    private Integer recruitNumber;

    /**
     * 组织机构ID
     */
    @Excel(name = "组织机构ID")
    @ApiModelProperty(value = "组织机构ID")
    private Long deptId;

    /**
     * 活动详情
     */
    @Excel(name = "活动详情")
    @ApiModelProperty(value = "活动详情")
    private String details;
    @ApiModelProperty(value = "活动图片地址")
    private String activityPictures;

    /**
     * 服务领域
     */
    @Excel(name = "服务领域")
    @ApiModelProperty(value = "服务领域")
    private String serviceField;

    /**
     * 服务对象
     */
    @Excel(name = "服务对象")
    @ApiModelProperty(value = "服务对象")
    private String serviceObject;

    /**
     * 服务场所
     */
    @Excel(name = "服务场所")
    @ApiModelProperty(value = "服务场所")
    private String serviceLocation;

    /**
     * 活动状态
     */
    @Excel(name = "活动状态")
    @ApiModelProperty(value = "活动状态")
    private String activityStatus;
    /**
     * 发布状态
     */
    @Excel(name = "发布状态")
    @ApiModelProperty(value = "发布状态")
    private String publishStatus;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 活动时长
     */
    @Excel(name = "活动时长")
    @ApiModelProperty(value = "活动时长")
    private Long duration;
    /**
     * 已招募人数
     */
    @ApiModelProperty(value = "已招募人数")
    @TableField(exist = false)
    private Long recruitedNumber;
    /**
     * 报名时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "报名时间")
    private Date signupDate;
    /**
     * 活动举办人
     */
    @ApiModelProperty(value = "活动举办人")
    private String leader;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(hidden = true)
    private SysDept dept;
    @ApiModelProperty(hidden = true)
    private Long fileId;
    @ApiModelProperty(hidden = true)
    private File file;

    @TableField(exist = false)
    private List<ActivityProcess> reviewLog;
    @TableField(exist = false)
    private List<ActivityUserRecord> signUpUsers;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("activityId", getActivityId())
                .append("activityName", getActivityName())
                .append("address", getAddress())
                .append("recruitNumber", getRecruitNumber())
                .append("deptId", getDeptId())
                .append("details", getDetails())
                .append("serviceField", getServiceField())
                .append("serviceObject", getServiceObject())
                .append("serviceLocation", getServiceLocation())
                .append("activityStatus", getActivityStatus())
                .append("publishStatus", getPublishStatus())
                .append("publishTime", getPublishTime())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("duration", getDuration())
                .toString();
    }
}
