package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/30 17:43
 */
@SystemLog(description = "个人中心")
@RequestMapping("/personal")
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @SystemLog(description = "查看登录日志")
    @RequestMapping("/getUserLoginRecordsByDay")
    @ResponseBody
    public WebResult getUserLoginRecordsByDay(String day) {
        return WebResult.successResult(userService.getUserLoginRecordsByDay(day));
    }

    @SystemLog(description = "查看个人信息")
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public WebResult getUserInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", ShiroUtils.getCurrentUser());
        map.put("loginRecord", userService.getUserLastLoginRecord());
        map.put("dateMap", userService.getUserLoginRecordDate());
        return WebResult.successResult(map);
    }

    @SystemLog(description = "修改用户信息")
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public WebResult updateUserInfo(User user) {
        return WebResult.successResult(userService.updateUserInfo(user));
    }

    @SystemLog(description = "修改用户密码")
    @RequestMapping("/updateUserPwd")
    @ResponseBody
    public WebResult updateUserPwd(String oldpassword, String newpassword) {
        return WebResult.successResult(userService.updateUserPwd(oldpassword, newpassword));
    }
}
