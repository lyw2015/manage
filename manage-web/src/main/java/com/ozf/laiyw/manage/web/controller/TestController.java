package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.service.inf.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/web")
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return testService.testFind();
    }
}
