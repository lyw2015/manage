package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.LoginRecordService;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import org.apache.log4j.Logger;
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
public class UserInfoController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private LoginRecordService loginRecordService;

    @SystemLog(description = "查看个人信息")
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public WebResult getUserInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", ShiroUtils.getCurrentUser());
        map.put("loginRecord", loginRecordService.getUserLastLoginRecord());
        map.put("dateMap", loginRecordService.getUserLoginRecordDate());
        return WebResult.successResult(map);
    }

    @SystemLog(description = "修改用户信息")
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public WebResult updateUserInfo(User user) {
        try {
            int count = userService.updateUserInfo(user);
            if (count == -2) {
                return WebResult.errorResult("修改失败，该邮箱已被其他账号绑定");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改用户信息错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改用户密码")
    @RequestMapping("/updateUserPwd")
    @ResponseBody
    public WebResult updateUserPwd(String oldpassword, String newpassword) {
        try {
            return WebResult.successResult(userService.updateUserPwd(oldpassword, newpassword));
        } catch (Exception e) {
            logger.error("修改用户密码错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "查看登录日志")
    @RequestMapping("/getUserLoginRecordsByDay")
    @ResponseBody
    public WebResult getUserLoginRecordsByDay(String day) {
        return WebResult.successResult(loginRecordService.getUserLoginRecordsByDay(day));
    }
}
