package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/29 19:39
 */
@RequestMapping("/common")
@Controller
public class CommonController extends BaseController {

    @Autowired
    private UserService userService;

    @SystemLog(description = "获取验证码")
    @RequestMapping("/getVerificationCode")
    @ResponseBody
    public WebResult getVerificationCode(String email) {
        return WebResult.successResult(userService.getVerificationCode(email));
    }

}
