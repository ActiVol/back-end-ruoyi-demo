package com.ruoyi.volunteer.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/open")
public class OpenController {

    @Autowired
    private EmailUtils emailUtils ;


    // http://127.0.0.1:8848/volunteer/open
    // http://47.97.99.65:8848/volunteer/open
    @GetMapping
    public String open() {
        System.out.println("当前时间为：" + System.currentTimeMillis());
        return "open";
    }


    /**
     * 发送纯文本邮件信息
     *
     * @param receiveEmail      接收方
     * @param title 邮件主题
     * @param content 邮件内容（发送内容）
     * http://127.0.0.1:8848/volunteer/email
     */
    @GetMapping(value = "/email")
    public AjaxResult email(
                @RequestParam(name = "receiveEmail", required = true) String receiveEmail,
                @RequestParam(name = "title", required = true) String title,
                @RequestParam(name = "content", required = true) String content) {
        emailUtils.sendMessage(receiveEmail, title, content);
        return AjaxResult.success();
    }
}
