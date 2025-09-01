package com.ruoyi.volunteer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("email_send_log")
public class EmailSendLog {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 邮件主题 */
    @Excel(name = "邮件主题")
    private String title;

    /** 邮件内容 */
    @Excel(name = "邮件内容")
    private String content;

    /** 邮件接收者Id */
    @Excel(name = "邮件接收者Id")
    private String receiverUserId;

    /** 邮件接收者名称 */
    @Excel(name = "邮件接收者名称")
    private String receiverUserName;

    /** 接收者邮箱 */
    @Excel(name = "接收者邮箱")
    private String receiverEmail;

    /** 发送人 */
    @Excel(name = "发送人")
    private String sendUserId;

    /** 发送人名称 */
    @Excel(name = "发送人名称")
    private String sendUserName;

    /** 发送时间 */
    @Excel(name = "发送时间")
    private String sendTime;


    private String isDelete;


    public EmailSendLog(String title, String content, String receiverUserId, String receiverUserName, String receiverEmail, String sendUserId, String sendUserName, String sendTime) {
        this.title = title;
        this.content = content;
        this.receiverUserId = receiverUserId;
        this.receiverUserName = receiverUserName;
        this.receiverEmail = receiverEmail;
        this.sendUserId = sendUserId;
        this.sendUserName = sendUserName;
        this.sendTime = sendTime;
    }
}
