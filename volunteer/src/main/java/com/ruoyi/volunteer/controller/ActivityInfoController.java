package com.ruoyi.volunteer.controller;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.service.IActivityInfoService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动信息Controller
 *
 * @author guyue
 * @date 2024-12-13
 */
@Api(tags = {"活动信息"})
@RestController
@RequestMapping("/volunteer/info")
public class ActivityInfoController extends BaseController {
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 查询活动信息列表
     */
    @ApiOperation(
            value = "® 查询活动信息列表",
            notes = "查询活动信息列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityInfo activityInfo) {
        startPage();
        List<ActivityInfo> list = activityInfoService.selectActivityInfoList(activityInfo);
        return getDataTable(list);
    }

    /**
     * 查询审核信息列表
     */
    @ApiOperation(
            value = "® 查询审核信息列表",
            notes = "查询审核信息列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:process:list')")
    @GetMapping("/process/list")
    public TableDataInfo processList(ActivityInfo activityInfo) {
        startPage();
        List<ActivityInfo> list = activityInfoService.selectActivityProcessList(activityInfo);
        return getDataTable(list);
    }
    @ApiOperation(
            value = "® 查询审核信息列表",
            notes = "查询审核信息列表"
    )
    @GetMapping("/getParticipatedActivitiesByToken")
    public AjaxResult getParticipatedActivitiesByToken() {
        ParticipatedActivitiesVo participatedActivities = activityInfoService.getParticipatedActivities(new ActivitySearchVo());
        return success(participatedActivities);
    }


    /**
     * 获取活动信息详细信息
     */
    @ApiOperation(
            value = "® 获取活动信息详细信息",
            notes = "获取活动信息详细信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:info:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId) {
        return success(activityInfoService.selectActivityInfoByActivityId(activityId));
    }

    /**
     * 新增活动信息
     */
    @ApiOperation(
            value = "® 新增活动信息",
            notes = "新增活动信息"
    )
    @Log(title = "活动信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityInfo activityInfo) {
        return toAjax(activityInfoService.insertActivityInfo(activityInfo));
    }




    /**
     * 修改活动信息
     */
    @ApiOperation(
            value = "® 修改活动信息",
            notes = "修改活动信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:info:edit')")
    @Log(title = "活动信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityInfo activityInfo) {
        return toAjax(activityInfoService.updateActivityInfo(activityInfo));
    }

    /**
     * 删除活动信息
     */
    @ApiOperation(
            value = "® 删除活动信息",
            notes = "删除活动信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:info:remove')")
    @Log(title = "活动信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds) {
        return toAjax(activityInfoService.deleteActivityInfoByActivityIds(activityIds));
    }


    /**
     * 提交活动信息
     */
    @PutMapping(value = "/commit/{activityId}")
    public AjaxResult commitActivityInfo(@PathVariable(name = "activityId") Long activityId,@RequestParam(name = "reviewComments") String reviewComments) {
        return activityInfoService.commitActivityInfo(activityId,reviewComments);
    }

    /**
     * 审核活动信息
     */
    @PutMapping(value = "/process/{activityId}")
    public AjaxResult processActivityInfo(@PathVariable(name = "activityId", required = true) Long activityId, @RequestParam(name = "reviewStatus", required = true) String reviewStatus,@RequestParam(name = "reviewComments") String reviewComments) throws Exception {
        return activityInfoService.processActivityInfo(activityId, reviewStatus,reviewComments);
    }

    /**
     * 发布活动信息
     */
    @ApiOperation(
            value = "® 发布活动信息",
            notes = "发布活动信息"
    )
    @Log(title = "发布活动信息", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/publish/{activityId}")
    public AjaxResult publish(@PathVariable(name = "activityId", required = true) Long activityId) {
        return toAjax(activityInfoService.publishActivityInfo(activityId));
    }

    /**
     * 结束活动 修改发布状态为已结束
     */
    @PutMapping(value = "/over/{activityId}")
    public AjaxResult overActivityInfo(@PathVariable(name = "activityId", required = true) Long activityId) {
        return toAjax(activityInfoService.overActivityInfo(activityId));
    }


    /**
     * 获取审核列表
     */
    @GetMapping(value = "/getAuditActivityList")
    public TableDataInfo getAuditActivityList(@RequestParam(name = "activityStatus", required = true) String activityStatus){
        startPage();
        List<ActivityInfo> auditActivityList = activityInfoService.getAuditActivityList(activityStatus);
        return getDataTable(auditActivityList);
    }


    /**
     * 活动报名
     */
    @ApiOperation(
            value = "® 活动报名",
            notes = "活动报名"
    )
    @Log(title = "活动报名", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/signUp/{activityId}")
    public AjaxResult signUp(@PathVariable(name = "activityId", required = true) Long activityId) {
        return activityInfoService.signUp(activityId);
    }

}
