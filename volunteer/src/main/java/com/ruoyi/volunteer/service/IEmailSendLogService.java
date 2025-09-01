package com.ruoyi.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.volunteer.domain.EmailSendLog;
import java.util.*;

public interface IEmailSendLogService {

    /**
     * 查询邮箱历史
     *
     * @param id 邮箱历史主键
     * @return 邮箱历史
     */
    public EmailSendLog selectEmailSendLogById(Long id);

    /**
     * 查询邮箱历史列表
     *
     * @param emailSendLog 邮箱历史
     * @return 邮箱历史集合
     */
    public List<EmailSendLog> selectEmailSendLogList(EmailSendLog emailSendLog);

    /**
     * 新增邮箱历史
     *
     * @param emailSendLog 邮箱历史
     * @return 结果
     */
    public int insertEmailSendLog(EmailSendLog emailSendLog);

    /**
     * 修改邮箱历史
     *
     * @param emailSendLog 邮箱历史
     * @return 结果
     */
    public int updateEmailSendLog(EmailSendLog emailSendLog);

    /**
     * 批量删除邮箱历史
     *
     * @param ids 需要删除的邮箱历史主键集合
     * @return 结果
     */
    public int deleteEmailSendLogByIds(Long[] ids);

    /**
     * 删除邮箱历史信息
     *
     * @param id 邮箱历史主键
     * @return 结果
     */
    public int deleteEmailSendLogById(Long id);

    List<SysUser> emailUsers();


    /**
     *  1. 组织者审核后发一次 邮件
     */
    void sendEmailToOrganizer(EmailSendLog emailSendLog,String activityId,boolean isPass);


    /**
     * 2. 活动结束，确认邮件，参加什么活动 获得多少时长，总时长。
     */
    void sendEmailToUser(EmailSendLog emailSendLog);



}
