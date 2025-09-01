package com.ruoyi.volunteer.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import com.ruoyi.volunteer.domain.TempActivities;
import com.ruoyi.volunteer.service.ITempActivitiesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 临时活动Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Api(tags = {"临时活动信息"})
@RestController
@RequestMapping("/volunteer/tempActivity")
public class TempActivitiesController extends BaseController {
    @Autowired
    private ITempActivitiesService tempActivitiesService;

    /**
     * 查询临时活动列表
     */
    @ApiOperation(
            value = "® 查询临时活动列表",
            notes = "查询临时活动列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:tempActivity:list')")
    @GetMapping("/list")
    public TableDataInfo list(TempActivities tempActivities) {
        startPage();
        List<TempActivities> list = tempActivitiesService.selectTempActivitiesList(tempActivities);
        return getDataTable(list);
    }

    /**
     * 导出临时活动列表
     */
    @ApiOperation(
            value = "® 导出临时活动列表",
            notes = "导出临时活动列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:tempActivity:export')")
    @Log(title = "临时活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TempActivities tempActivities) {
        List<TempActivities> list = tempActivitiesService.selectTempActivitiesList(tempActivities);
        ExcelUtil<TempActivities> util = new ExcelUtil<TempActivities>(TempActivities.class);
        util.exportExcel(response, list, "临时活动数据");
    }

    /**
     * 获取临时活动详细信息
     */
    @ApiOperation(
            value = "® 获取临时活动详细信息",
            notes = "获取临时活动详细信息"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "id",
            value = "活动ID"
    )})
    @PreAuthorize("@ss.hasPermi('volunteer:tempActivity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tempActivitiesService.selectTempActivitiesById(id));
    }


    /**
     * 新增临时活动
     */
    @ApiOperation(
            value = "® 新增临时活动",
            notes = "新增临时活动"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:tempActivity:add')")
    @Log(title = "临时活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TempActivities tempActivities) {
        return toAjax(tempActivitiesService.insertTempActivities(tempActivities));
    }

    /**
     * 修改临时活动
     */
    @ApiOperation(
            value = "® 修改临时活动",
            notes = "修改临时活动"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:tempActivity:edit')")
    @Log(title = "临时活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TempActivities tempActivities) {
        return toAjax(tempActivitiesService.updateTempActivities(tempActivities));
    }

    /**
     * 删除临时活动
     */
    @ApiOperation(
            value = "® 删除临时活动",
            notes = "删除临时活动"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "ids",
            value = "活动ID集合"
    )})
    @PreAuthorize("@ss.hasPermi('volunteer:tempActivity:remove')")
    @Log(title = "临时活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tempActivitiesService.deleteTempActivitiesByIds(ids));
    }

    /**
     * 临时活动审核
     *
     * @param id
     * @return
     */
    @ApiOperation(
            value = "® 临时活动审核",
            notes = "临时活动审核"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "id",
            value = "活动ID",
            dataTypeClass = Long.class
    )})
    @PutMapping("/processTempActivity/{id}")
    public AjaxResult processTempActivity(@PathVariable("id") Long id) {
        return toAjax(tempActivitiesService.processTempActivity(id));
    }
}
