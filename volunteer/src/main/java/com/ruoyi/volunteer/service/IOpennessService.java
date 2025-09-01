package com.ruoyi.volunteer.service;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.model.ForgotPasswordBody;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.TempActivities;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;

import java.util.List;

public interface IOpennessService {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 临时活动添加
     *
     * @param tempActivities 临时活动对象
     * @return
     */
    public int insertTempActivities(TempActivities tempActivities);

    /**
     * 获取发布的活动
     *
     * @return
     */
    List<ActivityInfo> getPublishActivities();

    /**
     * 查询活动信息
     *
     * @param activityId 活动信息主键
     * @return 活动信息
     */
    public ActivityInfo selectActivityInfoByActivityId(Long activityId);

    /**
     * 获取参加过的活动信息
     *
     * @param activitySearchVo
     * @return
     */
    ParticipatedActivitiesVo getParticipatedActivities(ActivitySearchVo activitySearchVo);

    String forgotPassword(ForgotPasswordBody forgotPasswordBody);

}
