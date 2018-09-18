package com.ozf.laiyw.manage.app.controller;

import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.exception.CustomException;
import com.ozf.laiyw.manage.service.inf.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "TestController", description = "测试")
@RequestMapping("/app")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(httpMethod = "GET", value = "测试查询", notes = "测试查询数据库数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "arg1", value = "参数一", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "arg2", value = "参数二", required = true, dataTypeClass = String.class)
    })
    @RequestMapping("/test")
    @ResponseBody
    public WebResult<String> test(String arg1, String arg2) {
        return WebResult.successResult(testService.testFind());
    }
}
