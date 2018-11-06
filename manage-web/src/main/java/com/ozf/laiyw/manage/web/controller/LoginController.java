package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.AddressUtils;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import com.ozf.laiyw.manage.shiro.core.CustomUsernamePasswordToken;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录/登出
 */
@SystemLog(description = "登录登出")
@RequestMapping("/manage")
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @SystemLog(description = "用户登录")
    @RequestMapping("/login")
    @ResponseBody
    public WebResult login(User user) {
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            CustomUsernamePasswordToken token = new CustomUsernamePasswordToken(
                    user.getAccount(),
                    user.getPassword(),
                    user.getRememberMe(),
                    userAgent.getOperatingSystem().getDeviceType().getName()
            );
            ShiroUtils.getSubject().login(token);
            userService.saveLoginRecord(userAgent, AddressUtils.getIpAddress(request));
            return WebResult.successResult("登录成功");
        } catch (IncorrectCredentialsException ice) {
            return WebResult.errorResult("验证未通过,错误的凭证");
        } catch (Exception e) {
            return WebResult.errorResult(e.getMessage());
        }
    }

    @SystemLog(description = "用户登出")
    @RequestMapping("/logout")
    public void logout() {
        ShiroUtils.getSubject().logout();
        redirect("login.html");
    }
}
