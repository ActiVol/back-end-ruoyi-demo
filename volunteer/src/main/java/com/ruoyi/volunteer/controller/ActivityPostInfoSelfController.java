package com.ruoyi.volunteer.controller;


import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.volunteer.domain.ActivityPostInfoSelf;
import com.ruoyi.volunteer.service.IActivityPostInfoSelfService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动信息Controller
 *
 * @author ruoyi
 * @date 2025-08-31
 */
@RestController
@RequestMapping("/activityInfo/selfRecord")
public class ActivityPostInfoSelfController extends BaseController
{
    @Autowired
    private IActivityPostInfoSelfService activityPostInfoSelfService;

    /**
     * 查询活动信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:self:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityPostInfoSelf activityPostInfoSelf)
    {
        startPage();
        List<ActivityPostInfoSelf> list = activityPostInfoSelfService.selectActivityPostInfoSelfList(activityPostInfoSelf);
        return getDataTable(list);
    }

    /**
     * 导出活动信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:self:export')")
    @Log(title = "活动信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityPostInfoSelf activityPostInfoSelf)
    {
        List<ActivityPostInfoSelf> list = activityPostInfoSelfService.selectActivityPostInfoSelfList(activityPostInfoSelf);
        ExcelUtil<ActivityPostInfoSelf> util = new ExcelUtil<ActivityPostInfoSelf>(ActivityPostInfoSelf.class);
        util.exportExcel(response, list, "活动信息数据");
    }

    /**
     * 获取活动信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:self:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return success(activityPostInfoSelfService.selectActivityPostInfoSelfByActivityId(activityId));
    }

    /**
     * 新增活动信息
     */
//    @PreAuthorize("@ss.hasPermi('system:self:add')")
    @Log(title = "活动信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityPostInfoSelf activityPostInfoSelf)
    {
        long time = new Date().getTime();
        activityPostInfoSelf.setApproveStatus(2L);
        String username = SecurityUtils.getUsername();
        activityPostInfoSelf.setCreateBy(SecurityUtils.getUserId()+"");
        activityPostInfoSelf.setCreateName(username);
        activityPostInfoSelf.setActivityId(time);
        return toAjax(activityPostInfoSelfService.insertActivityPostInfoSelf(activityPostInfoSelf));
    }

    /**
     * 修改活动信息
     */
    @PreAuthorize("@ss.hasPermi('system:self:edit')")
    @Log(title = "活动信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityPostInfoSelf activityPostInfoSelf)
    {
        return toAjax(activityPostInfoSelfService.updateActivityPostInfoSelf(activityPostInfoSelf));
    }

    /**
     * 删除活动信息
     */
    @PreAuthorize("@ss.hasPermi('system:self:remove')")
    @Log(title = "活动信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(activityPostInfoSelfService.deleteActivityPostInfoSelfByActivityIds(activityIds));
    }


    /**
     * 审核用户自己提交的活动信息
     * @param activityId
     * @param isPass
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('system:self:edit')")
    @GetMapping(value = "/userSelfActivityApprove")
    public AjaxResult userSelfActivityApprove( Long activityId, boolean isPass)
    {
        if (activityId==null ){
            return error("活动ID不能为空");
        }
        ActivityPostInfoSelf activityPostInfoSelf = new ActivityPostInfoSelf();
        activityPostInfoSelf.setActivityId(activityId);
        activityPostInfoSelf.setApproveStatus(isPass ? 3L : 4L);
        int i = activityPostInfoSelfService.updateActivityPostInfoSelf(activityPostInfoSelf);
        return success(i);
    }



}
