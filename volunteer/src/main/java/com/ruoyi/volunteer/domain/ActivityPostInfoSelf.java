package com.ruoyi.volunteer.domain;

import java.util.Date;
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
 * 活动信息对象 activity_post_info_self
 *
 * @author ruoyi
 * @date 2025-08-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityPostInfoSelf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String activityName;

    /** 活动时长 */
    @Excel(name = "活动时长")
    private Long duration;

    /** 招募人数 */
    @Excel(name = "招募人数")
    private Long recruitNumber;

    /** 活动地址 */
    @Excel(name = "活动地址")
    private String address;

    /** 服务领域 */
    @Excel(name = "服务领域")
    private String serviceField;

    /** 服务对象 */
    @Excel(name = "服务对象")
    private String serviceObject;

    /** 服务场所 */
    @Excel(name = "服务场所")
    private String serviceLocation;

    /** 举办者姓名 */
    @Excel(name = "举办者姓名")
    private String organizerName;

    /** 举办者邮箱 */
    @Excel(name = "举办者邮箱")
    private String organizerEmail;

    /** 活动详情 */
    @Excel(name = "活动详情")
    private String details;

    /** 活动图片 */
    @Excel(name = "活动图片")
    private String activityPictures;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" )
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 文件ID */
    @Excel(name = "文件ID")
    private Long fileId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private Long approveStatus;

    /** 申请人名称 */
    @Excel(name = "申请人名称")
    private String createName;


}
