package com.ruoyi.volunteer.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.mapper.FrontMapper;
import com.ruoyi.volunteer.service.IFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class FrontServiceImpl implements IFrontService {

    @Autowired
    private FrontMapper  frontMapper;
    @Override
    public Map<String, Object> viewState() {
        try{
            Long userId = SecurityUtils.getUserId();
            LoginUser loginUser = SecurityUtils.getLoginUser();
            // 达标时长
            Long durationOfQualify = loginUser.getUser().getDurationOfQualify();
            //  查询当前用户的所有活动时长
            Long userActivityDuration = frontMapper.getUserActivityDuration(userId);
            Long userActivityDurationSelf = frontMapper.getUserActivityDurationSelf(userId);
            // 查询当前用户的参与活动数量
            Long userParticipatedActivityCount = frontMapper.getUserParticipatedActivityCount(userId);

            Map<String,Object> map = new HashMap<>();
            map.put("totalHours",userActivityDuration+userActivityDurationSelf);
            map.put("needTotalHours",durationOfQualify);
            map.put("totalActivities",userParticipatedActivityCount);
            map.put("status","Completed");
            return map;
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("totalHours",0);
            map.put("needTotalHours",50);
            map.put("totalActivities",0);
            map.put("status","Completed");
            return map;
        }
    }


    @Override
    public List<Map<String,String>> activityDetails() {
        try{
            Long userId = SecurityUtils.getUserId();
            List<Map<String,String>> userActivityList = frontMapper.getUserActivityList(userId);
            return userActivityList;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public List<ActivityInfo> publishActivities() {
        return frontMapper.publishActivities();
    }
}
