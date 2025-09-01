package com.ruoyi.volunteer.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.model.ForgotPasswordBody;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.web.service.SysForgotPasswordService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.volunteer.domain.ActivityInfo;
import com.ruoyi.volunteer.domain.TempActivities;
import com.ruoyi.volunteer.domain.vo.ActivitySearchVo;
import com.ruoyi.volunteer.domain.vo.ParticipatedActivitiesVo;
import com.ruoyi.volunteer.service.IActivityInfoService;
import com.ruoyi.volunteer.service.IOpennessService;
import com.ruoyi.volunteer.service.IUserRoleApprovalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Api(tags = {"开放查询信息"})
@RestController
@RequestMapping("/openness")
public class OpennessController extends BaseController {

    @Autowired
    private ServerConfig serverConfig;


    @Autowired
    private IOpennessService opennessService;
    @Autowired
    private IUserRoleApprovalService userRoleApprovalService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @ApiOperation(
            value = "® 根据字典类型查询字典数据",
            notes = "根据字典类型查询字典数据"
    )
    @GetMapping(value = "/dict/{dictType}")
    public AjaxResult selectDictDataByType(@PathVariable String dictType) {
        List<SysDictData> data = opennessService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return success(data);
    }

    /**
     * 临时活动添加
     *
     * @param tempActivities 临时活动对象
     * @return
     */
    @ApiOperation(
            value = "® 临时活动添加",
            notes = "临时活动添加"
    )
    @PostMapping("/tempActivitie/add")
    public AjaxResult addTempAcitivity(@RequestBody TempActivities tempActivities) {
        return toAjax(opennessService.insertTempActivities(tempActivities));
    }

    /**
     * 获取发布的活动
     *
     * @return
     */
    @ApiOperation(
            value = "® 获取发布的活动",
            notes = "获取发布的活动"
    )
    @GetMapping("/activity/getPublishActivities")
    public TableDataInfo getPublishActivities() {
        startPage();
        List<ActivityInfo> list = opennessService.getPublishActivities();
        return getDataTable(list);
    }

    /**
     * 获取活动信息详情
     *
     * @param activityId
     * @return
     */
    @ApiOperation(
            value = "® 获取活动信息详情",
            notes = "获取活动信息详情"
    )
    @GetMapping("/activity/getActivityDetailById/{activityId}")
    public AjaxResult getActivityDetailById(@PathVariable("activityId") Long activityId) {
        return success(opennessService.selectActivityInfoByActivityId(activityId));
    }

    /**
     * 获取参加过的活动信息
     *
     * @param activitySearchVo
     * @return
     */
    @ApiOperation(
            value = "® 获取参加过的活动信息",
            notes = "获取参加过的活动信息"
    )
    @PostMapping("/activity/getParticipatedActivities")
    public AjaxResult getParticipatedActivities(@Validated @RequestBody ActivitySearchVo activitySearchVo) {
        ParticipatedActivitiesVo participatedActivitiesVo = opennessService.getParticipatedActivities(activitySearchVo);
        return success(participatedActivitiesVo);
    }

    /**
     * 通用上传请求（单个）
     */
    @ApiOperation(
            value = "® 通用上传请求",
            notes = "通用上传请求"
    )
    @PostMapping("/upload/image")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
    @PostMapping("/user/register")
    public AjaxResult register(@Validated @RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String userType = user.getUserType();
        String msg = userRoleApprovalService.H5registerUser(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
    @Autowired
    private SysForgotPasswordService sysForgotPasswordService;
    @PostMapping("/forgotPassword")
    public AjaxResult forgotPassword(@RequestBody ForgotPasswordBody forgotPasswordBody) {
        String msg = opennessService.forgotPassword(forgotPasswordBody);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }




}
