package com.ruoyi.volunteer.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EmailUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.volunteer.domain.EmailSendLog;
import com.ruoyi.volunteer.service.IEmailSendLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 邮箱历史Controller
 *
 * @author ruoyi
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/system/log")
public class EmailSendLogController extends BaseController
{
    @Autowired
    private IEmailSendLogService emailSendLogService;

    @Autowired
    private EmailUtils emailUtils ;

    @Autowired
    ISysUserService sysUserService;

    /**
     * 查询邮箱历史列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(EmailSendLog emailSendLog)
    {
        startPage();
        List<EmailSendLog> list = emailSendLogService.selectEmailSendLogList(emailSendLog);
        return getDataTable(list);
    }

    /**
     * 导出邮箱历史列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "邮箱历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmailSendLog emailSendLog)
    {
        List<EmailSendLog> list = emailSendLogService.selectEmailSendLogList(emailSendLog);
        ExcelUtil<EmailSendLog> util = new ExcelUtil<EmailSendLog>(EmailSendLog.class);
        util.exportExcel(response, list, "邮箱历史数据");
    }

    /**
     * 获取邮箱历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(emailSendLogService.selectEmailSendLogById(id));
    }

    /**
     * 新增邮箱历史
     */
    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "邮箱历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmailSendLog emailSendLog)
    {
        return toAjax(emailSendLogService.insertEmailSendLog(emailSendLog));
    }

    /**
     * 修改邮箱历史
     */
    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "邮箱历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmailSendLog emailSendLog)
    {
        return toAjax(emailSendLogService.updateEmailSendLog(emailSendLog));
    }

    /**
     * 删除邮箱历史
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "邮箱历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(emailSendLogService.deleteEmailSendLogByIds(ids));
    }


    /**
     * 邮件接收者列表
     * @return
     */
    @GetMapping("/emailUsers")
    public TableDataInfo emailUsers()
    {
        startPage();
        List<SysUser> sysUsers = emailSendLogService.emailUsers();
        return getDataTable(sysUsers);
    }


    /**
     * 邮件发送
     */
    @PutMapping(value = "/emailSend")
    public AjaxResult emailSend(
            @RequestParam(name = "receiveUserId", required = true) String receiveUserId,
            @RequestParam(name = "receiveEmail", required = true) String receiveEmail,
            @RequestParam(name = "title", required = true) String title,
            @RequestParam(name = "content", required = true) String content)
    {
        emailUtils.sendMessage(receiveEmail, title, content);
        SysUser receiveUser = sysUserService.selectUserById(Long.valueOf(receiveUserId));
        EmailSendLog emailSendLog = new EmailSendLog(title, content, receiveUserId, receiveUser.getUserName(), receiveEmail, SecurityUtils.getUserId()+"", SecurityUtils.getUsername(), DateUtils.getTime());
        emailSendLogService.insertEmailSendLog(emailSendLog);
        return AjaxResult.success();
    }



}
