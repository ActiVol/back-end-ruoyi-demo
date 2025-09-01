package com.ruoyi.volunteer.service.impl;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ForgotPasswordBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EmailUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.TempActivities;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;
import com.ruoyi.volunteer.mapper.ActivityInfoMapper;
import com.ruoyi.volunteer.service.IOpennessService;
import com.ruoyi.volunteer.service.ITempActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OpennessServiceImpl implements IOpennessService {
    @Autowired
    private ISysDictTypeService dictTypeService;
    @Autowired
    private ITempActivitiesService tempActivitiesService;
    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private EmailUtils emailUtils;
    private static final String KEY_PREFIX = "RESET_PASSWORD";

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return dictTypeService.selectDictDataByType(dictType);
    }

    /**
     * 临时活动添加
     *
     * @param tempActivities 临时活动对象
     * @return
     */
    @Override
    public int insertTempActivities(TempActivities tempActivities) {
        return tempActivitiesService.insertTempActivities(tempActivities);
    }

    /**
     * 获取发布的活动
     *
     * @return
     */
    @Override
    public List<ActivityInfo> getPublishActivities() {
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setPublishStatus("3");// 已发布
        activityInfo.setActivityStatus("3");// 审核通过
        return activityInfoMapper.getPublishActivities(activityInfo);
    }

    /**
     * 获取活动信息详情
     *
     * @param activityId
     * @return
     */
    @Override
    public ActivityInfo selectActivityInfoByActivityId(Long activityId) {
        return activityInfoMapper.selectActivityInfoByActivityId(activityId);
    }

    @Override
    public ParticipatedActivitiesVo getParticipatedActivities(ActivitySearchVo activitySearchVo) {
        ParticipatedActivitiesVo participatedActivitiesVo = new ParticipatedActivitiesVo();
        SysUser user = sysUserService.selectUserByUserNameAndEmail(activitySearchVo.getUsername(),activitySearchVo.getEmail());
        if (user == null) {
            return participatedActivitiesVo;
        } else {
            Long userId = user.getUserId();
            List<ActivityInfo> activityInfos = activityInfoMapper.getParticipatedActivities(activitySearchVo);
            if (!CollectionUtils.isEmpty(activityInfos)) {
                long accumulatedDuration = activityInfos
                        .stream()
                        .filter(activityInfo -> activityInfo.getDuration() != null) // 过滤掉duration为null的情况
                        .mapToLong(ActivityInfo::getDuration) // 将每个 ActivityInfo 转换成其 duration
                        .sum();
                participatedActivitiesVo.setActivityInfo(activityInfos);
                participatedActivitiesVo.setPartivipatedCount(activityInfos.size());
                participatedActivitiesVo.setAccumulatedDuration(accumulatedDuration);
            }
            String username = user.getUserName();
            String email = user.getExternalEmail();
            String avatar = user.getAvatar();
            participatedActivitiesVo.setEmail(email);
            participatedActivitiesVo.setUsername(username);
            participatedActivitiesVo.setAvatar(avatar);
            participatedActivitiesVo.setUserId(userId);
            return participatedActivitiesVo;
        }
    }

    public String forgotPassword(ForgotPasswordBody forgotPasswordBody) {
        String msg = "",email = forgotPasswordBody.getEmail();
        SysUser user = new SysUser();
        if(StringUtils.isEmpty(email)){
            msg = "邮箱不能为空";
        }
        user.setExternalEmail(email);
        validateCaptcha(forgotPasswordBody.getCode(), forgotPasswordBody.getUuid());
        boolean flag = sysUserService.checkEmailUnique(user);
        if(flag){
            msg = "邮箱'" + email + "'不存在";
        }else{
            SysUser sysUser = sysUserService.selectUserByEmail(email);
            buildContent(sysUser);
        }
        return msg;
    }
    public void buildContent(SysUser sysUser){
        String email = sysUser.getExternalEmail();
        String emailTemp = configService.selectConfigByKey("sys.forgotpassword.emailTemplate");
        String subject = configService.selectConfigByKey("sys.forgotpassword.emailSubjectTemplate");
        String userName = sysUser.getUserName();
        String uuid = IdUtils.simpleUUID();
        String date = DateUtils.getDate();
        String content = StringUtils.format(emailTemp, userName, uuid, date);
        int  expireTime = 1000*60*60*48;
        String key = KEY_PREFIX+uuid;
        redisCache.setCacheObject(key,sysUser,expireTime, TimeUnit.MINUTES);
        emailUtils.sendMessage(email,subject,content);
//        EmailSendLog emailSendLog = new EmailSendLog();
//        emailSendLog.setBody(content);
//        emailSendLog.setSubject(subject);
//        emailSendLog.setRecipientEmail(email);
//        emailSendLog.setCreateTime(DateUtils.getNowDate());
//        emailSendLogService.insertEmailSendLog(emailSendLog);
    }

    /**
     * 校验验证码
     *
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha( String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }

}
