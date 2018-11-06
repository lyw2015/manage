package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.refrsh.XmlRefreshWebApplicationContext;
import com.ozf.laiyw.manage.service.TestService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

@SystemLog(description = "测试")
@RequestMapping("/web")
@Controller
public class TestController extends BaseController {

    @Autowired
    @Qualifier("testServiceImpl")
    private TestService testService;

    @SystemLog(description = "刷新系统配置")
    @RequestMapping("/reload")
    @ResponseBody
    public Object reload() {
        XmlRefreshWebApplicationContext applicationContext = (XmlRefreshWebApplicationContext) ContextLoader
                .getCurrentWebApplicationContext();
        applicationContext.reload();
        return "success";
    }

    @SystemLog(description = "admin:query,admin:test")
    @RequiresPermissions(value = {"admin:query", "admin:test"})

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return testService.testFind();
    }

    @SystemLog(description = "admin:query")
    @RequiresPermissions(value = {"admin:query"})
    @RequestMapping("/query")
    @ResponseBody
    public String query() {
        return testService.testFind();
    }
}
