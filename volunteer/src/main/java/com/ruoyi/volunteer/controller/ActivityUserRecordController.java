package com.ruoyi.volunteer.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.volunteer.domain.ActivityUserRecord;
import com.ruoyi.volunteer.service.IActivityUserRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户报名活动记录Controller
 *
 * @author ruoyi
 * @date 2025-07-13
 */
@RestController
@RequestMapping("/volunteer/userRecord")
public class ActivityUserRecordController extends BaseController
{
    @Autowired
    private IActivityUserRecordService activityUserRecordService;

    /**
     * 查询用户报名活动记录列表
     */
    @PreAuthorize("@ss.hasPermi('volunteer:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityUserRecord activityUserRecord)
    {
        startPage();
        List<ActivityUserRecord> list = activityUserRecordService.selectActivityUserRecordList(activityUserRecord);
        return getDataTable(list);
    }

    /**
     * 导出用户报名活动记录列表
     */
    @PreAuthorize("@ss.hasPermi('volunteer:record:export')")
    @Log(title = "用户报名活动记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityUserRecord activityUserRecord)
    {
        List<ActivityUserRecord> list = activityUserRecordService.selectActivityUserRecordList(activityUserRecord);
        ExcelUtil<ActivityUserRecord> util = new ExcelUtil<ActivityUserRecord>(ActivityUserRecord.class);
        util.exportExcel(response, list, "用户报名活动记录数据");
    }

    /**
     * 获取用户报名活动记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('volunteer:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(activityUserRecordService.selectActivityUserRecordById(id));
    }

    /**
     * 新增用户报名活动记录
     */
    @PreAuthorize("@ss.hasPermi('volunteer:record:add')")
    @Log(title = "用户报名活动记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityUserRecord activityUserRecord)
    {
        return toAjax(activityUserRecordService.insertActivityUserRecord(activityUserRecord));
    }

    /**
     * 修改用户报名活动记录
     */
    @PreAuthorize("@ss.hasPermi('volunteer:record:edit')")
    @Log(title = "用户报名活动记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityUserRecord activityUserRecord)
    {
        return toAjax(activityUserRecordService.updateActivityUserRecord(activityUserRecord));
    }

    /**
     * 删除用户报名活动记录
     */
    @PreAuthorize("@ss.hasPermi('volunteer:record:remove')")
    @Log(title = "用户报名活动记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activityUserRecordService.deleteActivityUserRecordByIds(ids));
    }


    /**
     * 报名通过
     */
    @PutMapping("/signUpPass")
    public AjaxResult signUpPass(@RequestParam(name = "activityId") String activityId,@RequestParam(name = "userId") String userId) {
        return activityUserRecordService.signUpPass(activityId,userId);
    }

    /**
     * 报名拒绝
     */
    @PutMapping("/signUpReject")
    public AjaxResult signUpReject(@RequestParam(name = "activityId") String activityId,@RequestParam(name = "userId") String userId) {
        return activityUserRecordService.signUpReject(activityId,userId);
    }

    /**
     * 报名取消
     */
    @PutMapping("/signUpCancel")
    public AjaxResult signUpCancel(@RequestParam(name = "activityId") String activityId,@RequestParam(name = "userId") String userId) {
        return activityUserRecordService.signUpCancel(activityId,userId);
    }


    /**
     * zzzPendingReviewList
     */
//    @PreAuthorize("@ss.hasPermi('volunteer:record:list')")
    @GetMapping("/zzzPendingReviewList")
    public TableDataInfo zzzPendingReviewList() {
        startPage();
        List<Map<String, String>> maps = activityUserRecordService.zzzPendingReviewList();
        return getDataTable(maps);
    }


}
