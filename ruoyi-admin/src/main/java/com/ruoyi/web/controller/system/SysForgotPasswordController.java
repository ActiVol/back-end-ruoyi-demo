package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.ForgotPasswordBody;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * 重置密码
 *
 * @author ruoyi
 */
@RestController
public class SysForgotPasswordController extends BaseController {
    @Autowired
    private SysForgotPasswordService sysForgotPasswordService;

    @PostMapping("/resetPasswordByUrl")
    public AjaxResult resetPasswordByUrl(@RequestBody LoginBody loginBody) {
        String msg = sysForgotPasswordService.resetPasswordByUrl(loginBody);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

}
