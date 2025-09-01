package com.ruoyi.volunteer.service;

import com.ruoyi.volunteer.domain.ActivityInfo;

import java.util.List;
import java.util.Map;

public interface IFrontService {

    Map<String ,Object> viewState();

    List<Map<String,String>> activityDetails();

    List<ActivityInfo> publishActivities();

}
