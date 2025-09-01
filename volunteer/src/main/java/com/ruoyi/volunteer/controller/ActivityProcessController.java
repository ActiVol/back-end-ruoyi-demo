package com.ruoyi.volunteer.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.volunteer.domain.ActivityProcess;
import com.ruoyi.volunteer.service.IActivityProcessService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 审核信息Controller
 *
 * @author guyue
 * @date 2024-12-13
 */
@Api(tags = {"活动审核信息"})
@RestController
@RequestMapping("/volunteer/process")
public class ActivityProcessController extends BaseController {
    @Autowired
    private IActivityProcessService activityProcessService;

    /**
     * 查询审核信息列表
     */
    @ApiOperation(
            value = "® 查询审核信息列表",
            notes = "查询审核信息列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:process:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityProcess activityProcess) {
        startPage();
        List<ActivityProcess> list = activityProcessService.selectActivityProcessList(activityProcess);
        return getDataTable(list);
    }

    /**
     * 导出审核信息列表
     */
    @ApiOperation(
            value = "® 导出审核信息列表",
            notes = "导出审核信息列表"
    )
    @Log(title = "审核信息", businessType = BusinessType.UPDATE)
    @PostMapping("/activity")
    public AjaxResult processActivity(@RequestBody ActivityProcess activityProcess) throws Exception {
        return success(activityProcessService.processActivity(activityProcess));
    }

    /**
     * 获取审核信息详细信息
     */
    @ApiOperation(
            value = "® 获取审核信息详细信息",
            notes = "获取审核信息详细信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:process:query')")
    @GetMapping(value = "/{processId}")
    public AjaxResult getInfo(@PathVariable("processId") Long processId) {
        return success(activityProcessService.selectActivityProcessByProcessId(processId));
    }

    /**
     * 新增审核信息
     */
    @ApiOperation(
            value = "® 新增审核信息",
            notes = "新增审核信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:process:add')")
    @Log(title = "审核信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityProcess activityProcess) {
        return toAjax(activityProcessService.insertActivityProcess(activityProcess));
    }

    /**
     * 修改审核信息
     */
    @ApiOperation(
            value = "® 修改审核信息",
            notes = "修改审核信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:process:edit')")
    @Log(title = "审核信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityProcess activityProcess) {
        return toAjax(activityProcessService.updateActivityProcess(activityProcess));
    }

    /**
     * 删除审核信息
     */
    @ApiOperation(
            value = "® 删除审核信息",
            notes = "删除审核信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:process:remove')")
    @Log(title = "审核信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{processIds}")
    public AjaxResult remove(@PathVariable Long[] processIds) {
        return toAjax(activityProcessService.deleteActivityProcessByProcessIds(processIds));
    }
}
