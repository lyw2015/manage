package com.ozf.laiyw.manage.web.controller.base;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.EmailUtils;
import com.ozf.laiyw.manage.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 17:04
 */
@SystemLog(description = "系统配置")
@RequestMapping("/system")
@Controller
public class SystemController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SystemService systemService;

    @SystemLog(description = "测试邮件服务")
    @RequestMapping("/testConnection")
    @ResponseBody
    public WebResult testConnection(String host, Integer port, String username, String password) {
        return WebResult.successResult(EmailUtils.testConnection(host, port, username, password));
    }

    @SystemLog(description = "修改邮件服务配置")
    @RequestMapping("/updateEmailServerConfig")
    @ResponseBody
    public WebResult updateEmailServerConfig(String jsondata) {
        try {
            systemService.updateSystemConfig(Constants.EMAIL_SERVER, jsondata);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改邮件服务配置错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

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
        try {
            systemService.updateSystemConfig(Constants.LOGIN_RULE, jsondata);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改登录规则错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }
}
