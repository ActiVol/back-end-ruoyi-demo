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
import com.ruoyi.volunteer.domain.File;
import com.ruoyi.volunteer.service.IFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件Controller
 *
 * @author ruoyi
 * @date 2025-01-17
 */
@Api(tags = {"文件信息"})
@RestController
@RequestMapping("/volunteer/file")
public class FileController extends BaseController {
    @Autowired
    private IFileService fileService;

    /**
     * 查询文件列表
     */
    @ApiOperation(
            value = "® 查询文件列表",
            notes = "查询文件列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(File file) {
        startPage();
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 导出文件列表
     */
    @ApiOperation(
            value = "® 导出文件列表",
            notes = "导出文件列表"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:file:export')")
    @Log(title = "文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, File file) {
        List<File> list = fileService.selectFileList(file);
        ExcelUtil<File> util = new ExcelUtil<File>(File.class);
        util.exportExcel(response, list, "文件数据");
    }

    /**
     * 获取文件详细信息
     */
    @ApiOperation(
            value = "® 获取文件详细信息",
            notes = "获取文件详细信息"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:file:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(fileService.selectFileById(id));
    }
    /**
     * 删除文件
     */
    @ApiOperation(
            value = "® 删除文件",
            notes = "删除文件"
    )
    @PreAuthorize("@ss.hasPermi('volunteer:file:remove')")
    @Log(title = "文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(fileService.deleteFileById(id));
    }
}
