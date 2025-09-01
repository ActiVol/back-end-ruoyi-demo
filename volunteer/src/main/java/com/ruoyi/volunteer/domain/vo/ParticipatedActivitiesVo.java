package com.ruoyi.volunteer.domain.vo;

import com.ruoyi.volunteer.domain.ActivityInfo;

import java.math.BigInteger;
import java.util.List;

public class ParticipatedActivitiesVo {
    private List<ActivityInfo> activityInfo;
    // 累计时长
    private Long accumulatedDuration;
    // 累计活动
    private Integer partivipatedCount;
    // 姓名
    private String username;
    // 邮箱
    private String email;
    // 头像
    private String avatar;
    // 用户ID
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ActivityInfo> getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(List<ActivityInfo> activityInfo) {
        this.activityInfo = activityInfo;
    }

    public Long getAccumulatedDuration() {
        return accumulatedDuration;
    }

    public void setAccumulatedDuration(Long accumulatedDuration) {
        this.accumulatedDuration = accumulatedDuration;
    }

    public Integer getPartivipatedCount() {
        return partivipatedCount;
    }

    public void setPartivipatedCount(Integer partivipatedCount) {
        this.partivipatedCount = partivipatedCount;
    }
}
