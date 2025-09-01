package com.ruoyi.volunteer.mapper;

import com.ruoyi.volunteer.domain.ActivityInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FrontMapper {

    //  查询当前用户的所有活动时长
    public Long getUserActivityDuration(Long userId);
    public Long getUserActivityDurationSelf(Long userId);

    // 查询当前用户的参与活动数量
    public Long getUserParticipatedActivityCount(Long userId);

    // 查询当前用户的活动列表
    public List<Map<String,String>> getUserActivityList(Long userId);

    public List<ActivityInfo> publishActivities();


}
