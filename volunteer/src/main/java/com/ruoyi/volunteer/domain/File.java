package com.ruoyi.volunteer.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件对象 file
 *
 * @author ruoyi
 * @date 2025-01-17
 */
public class File extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文件编号
     */
    @ApiModelProperty(value = "文件编号")
    private Long fileId;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    @Excel(name = "文件名")
    private String fileName;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String path;

    /**
     * 文件 URL
     */
    @Excel(name = "文件 URL")
    @ApiModelProperty(value = "文件 URL")
    private String url;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    @ApiModelProperty(value = "文件类型")
    private String type;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    @ApiModelProperty(value = "文件大小")
    private Long size;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer deleted;
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fileId", getFileId())
                .append("name", getFileName())
                .append("path", getPath())
                .append("url", getUrl())
                .append("type", getType())
                .append("size", getSize())
                .append("deleted", getDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
