package com.ozf.laiyw.manage.app.controller;

import com.ozf.laiyw.manage.service.inf.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return testService.testFind();
    }
}
