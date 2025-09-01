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
import com.ruoyi.volunteer.domain.UserRoleApproval;
import com.ruoyi.volunteer.service.IUserRoleApprovalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户角色审核Controller
 *
 * @author ruoyi
 * @date 2025-03-16
 */
@Api("用户角色审核")
@RestController
@RequestMapping("/volunteer/userRole")
public class UserRoleApprovalController extends BaseController {
    @Autowired
    private IUserRoleApprovalService userRoleApprovalService;

    /**
     * 查询用户角色审核列表
     */
    @ApiOperation(
            value = "® 查询用户角色审核列表",
            notes = "查询用户角色审核列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:userRole:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserRoleApproval userRoleApproval) {
        startPage();
        List<UserRoleApproval> list = userRoleApprovalService.selectUserRoleApprovalList(userRoleApproval);
        return getDataTable(list);
    }


    /**
     * 获取用户角色审核详细信息
     */
    @ApiOperation(
            value = "® 获取用户角色审核详细信息",
            notes = "获取用户角色审核详细信息"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "id"
    )})
    @PreAuthorize("@ss.hasPermi('volunteer:userRole:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userRoleApprovalService.selectUserRoleApprovalById(id));
    }


    /**
     * 用户角色审核
     */
    @ApiOperation(
            value = "® 用户角色审核",
            notes = "用户角色审核"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:userRole:approval')")
    @Log(title = "用户角色审核", businessType = BusinessType.UPDATE)
    @PutMapping("/approval")
    public AjaxResult approval(@RequestBody UserRoleApproval userRoleApproval) {
        return toAjax(userRoleApprovalService.approval(userRoleApproval));
    }


}
