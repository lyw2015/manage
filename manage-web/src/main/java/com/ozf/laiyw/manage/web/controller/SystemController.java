package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 17:04
 */
@RequestMapping("/system")
@Controller
public class SystemController extends BaseController {

    @Autowired
    private SystemService systemService;

    @SystemLog(description = "查看系统配置")
    @RequestMapping("/getConfigs")
    @ResponseBody
    public WebResult getConfigs() {
        return WebResult.successResult(systemService.getConfigs());
    }

    @SystemLog(description = "修改登录规则")
    @RequestMapping("/updateLoginRuleConfig")
    @ResponseBody
    public WebResult updateLoginRuleConfig(String jsondata) {
        return WebResult.successResult(systemService.updateSystemConfig(Constants.LOGIN_RULE, jsondata));
    }
}
