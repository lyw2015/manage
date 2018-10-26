package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.service.TestService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/web")
@Controller
public class TestController {

    @Autowired
    @Qualifier("testServiceImpl")
    private TestService testService;

    @RequiresPermissions(value = {"admin:query", "admin:test"})
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return testService.testFind();
    }

    @RequiresPermissions(value = {"admin:query"})
    @RequestMapping("/query")
    @ResponseBody
    public String query() {
        return testService.testFind();
    }
}
