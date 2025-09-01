package com.ruoyi.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EmailUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.EmailSendLog;
import com.ruoyi.volunteer.mapper.ActivityInfoMapper;
import com.ruoyi.volunteer.mapper.EmailSendLogMapper;
import com.ruoyi.volunteer.service.IActivityInfoService;
import com.ruoyi.volunteer.service.IEmailSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class EmailSendLogServiceImpl extends ServiceImpl<EmailSendLogMapper, EmailSendLog> implements IEmailSendLogService {

    @Autowired
    private EmailSendLogMapper emailSendLogMapper;

    @Autowired
    private EmailUtils emailUtils ;

    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ActivityInfoMapper activityInfoMapper;
    @Autowired
    IActivityInfoService activityInfoService;

    /**
     * 查询邮箱历史
     *
     * @param id 邮箱历史主键
     * @return 邮箱历史
     */
    @Override
    public EmailSendLog selectEmailSendLogById(Long id)
    {
        return emailSendLogMapper.selectById(id);
    }

    /**
     * 查询邮箱历史列表
     *
     * @param emailSendLog 邮箱历史
     * @return 邮箱历史
     */
    @Override
    public List<EmailSendLog> selectEmailSendLogList(EmailSendLog emailSendLog)
    {
        LambdaQueryWrapper<EmailSendLog> lqw = new LambdaQueryWrapper<>();
        lqw.eq(EmailSendLog::getIsDelete, "0");
        lqw.like(emailSendLog.getTitle() != null, EmailSendLog::getTitle, emailSendLog.getTitle());
        lqw.like(emailSendLog.getContent() != null, EmailSendLog::getContent, emailSendLog.getContent());
        lqw.eq(emailSendLog.getReceiverUserId() != null, EmailSendLog::getReceiverUserId, emailSendLog.getReceiverUserId());
        lqw.like(emailSendLog.getReceiverUserName() != null, EmailSendLog::getReceiverUserName, emailSendLog.getReceiverUserName());
        lqw.like(emailSendLog.getReceiverEmail() != null, EmailSendLog::getReceiverEmail, emailSendLog.getReceiverEmail());
        lqw.eq(emailSendLog.getSendUserId() != null, EmailSendLog::getSendUserId, emailSendLog.getSendUserId());
        lqw.like(emailSendLog.getSendUserName() != null, EmailSendLog::getSendUserName, emailSendLog.getSendUserName());
        lqw.like(emailSendLog.getSendTime() != null, EmailSendLog::getSendTime, emailSendLog.getSendTime());
        return emailSendLogMapper.selectList(lqw);
    }

    /**
     * 新增邮箱历史
     *
     * @param emailSendLog 邮箱历史
     * @return 结果
     */
    @Override
    public int insertEmailSendLog(EmailSendLog emailSendLog)
    {
        return emailSendLogMapper.insert(emailSendLog);
    }

    /**
     * 修改邮箱历史
     *
     * @param emailSendLog 邮箱历史
     * @return 结果
     */
    @Override
    public int updateEmailSendLog(EmailSendLog emailSendLog)
    {
        return emailSendLogMapper.updateById(emailSendLog);
    }

    /**
     * 批量删除邮箱历史
     *
     * @param ids 需要删除的邮箱历史主键
     * @return 结果
     */
    @Override
    public int deleteEmailSendLogByIds(Long[] ids)
    {
        LambdaUpdateWrapper<EmailSendLog> luw = new LambdaUpdateWrapper<EmailSendLog>().in(EmailSendLog::getId, ids).set(EmailSendLog::getIsDelete, "1");
        return emailSendLogMapper.update(null,luw);
    }

    /**
     * 删除邮箱历史信息
     *
     * @param id 邮箱历史主键
     * @return 结果
     */
    @Override
    public int deleteEmailSendLogById(Long id)
    {
        LambdaUpdateWrapper<EmailSendLog> luw = new LambdaUpdateWrapper<>();
        luw.eq(EmailSendLog::getId, id);
        luw.set(EmailSendLog::getIsDelete, "1");
        return emailSendLogMapper.update(null,luw);
    }


    @Override
    public List<SysUser> emailUsers() {
        return emailSendLogMapper.emailUsers();
    }


    @Override
    public void sendEmailToOrganizer(EmailSendLog emailSendLog,String activityId,boolean isPass) {
        ActivityInfo activityInfo = activityInfoService.selectActivityInfoByActivityId(Long.valueOf(activityId));
        String title = activityInfo.getActivityName() + "审核结果";
        String content = isPass ? "恭喜您，您的活动审核通过！" : "抱歉，您的活动审核未通过！";
        String receiverUserId = emailSendLog.getReceiverUserId();
        SysUser sysUser = sysUserService.selectUserById(Long.valueOf(receiverUserId));
        String receiverEmail = sysUser.getExternalEmail();
        emailUtils.sendMessage(receiverEmail, title, content);
        emailSendLog.setTitle(title);
        emailSendLog.setContent(content);
        emailSendLog.setReceiverUserName(sysUser.getUserName());
        emailSendLog.setReceiverEmail(receiverEmail);
        emailSendLog.setSendUserName("0");
        emailSendLog.setSendUserName("系统");
        emailSendLog.setSendTime(DateUtils.getTime());
        emailSendLogMapper.insert(emailSendLog);
    }

    @Override
    public void sendEmailToUser(EmailSendLog emailSendLog) {

    }
}
