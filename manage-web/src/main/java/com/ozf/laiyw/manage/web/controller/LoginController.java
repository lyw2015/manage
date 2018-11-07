package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.AddressUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import com.ozf.laiyw.manage.shiro.core.CustomUsernamePasswordToken;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
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

    @RequestMapping("/getVerificationCode")
    @ResponseBody
    public WebResult getVerificationCode(String email) {
        return WebResult.successResult(userService.getVerificationCode(email));
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public WebResult checkEmail(String email) {
        if (null == userService.getUserByEmail(email)) {
            return WebResult.successResult(false);
        }
        return WebResult.successResult(true);
    }

    @SystemLog(description = "验证码登录")
    @RequestMapping("/verificationCodeLogin")
    @ResponseBody
    public WebResult verificationCodeLogin(User user) {
        if (StringUtils.isNotEmpty(user.getMailbox()) && StringUtils.isNotEmpty(user.getVerificationCode())) {
            boolean bl = userService.checkVerificationCode(user.getMailbox(), user.getVerificationCode());
            if (bl) {
                return executeLogin(new CustomUsernamePasswordToken(user.getMailbox(), user.getRememberMe()));
            }
            return WebResult.errorResult(Constants.INCORRECTCREDENTIALSEXCEPTION);
        }
        return WebResult.errorResult(Constants.INCORRECTCREDENTIALSEXCEPTION);
    }

    @SystemLog(description = "用户登录")
    @RequestMapping("/login")
    @ResponseBody
    public WebResult login(User user) {
        if (StringUtils.isEmpty(user.getPassword()))
            return WebResult.errorResult(Constants.INCORRECTCREDENTIALSEXCEPTION);
        return executeLogin(new CustomUsernamePasswordToken(
                user.getAccount(),
                user.getPassword(),
                user.getRememberMe()
        ));
    }

    private WebResult executeLogin(UsernamePasswordToken token) {
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            ShiroUtils.getSubject().login(token);
            userService.saveLoginRecord(userAgent, AddressUtils.getIpAddress(request));
            return WebResult.successResult("登录成功");
        } catch (IncorrectCredentialsException ice) {
            return WebResult.errorResult(Constants.INCORRECTCREDENTIALSEXCEPTION);
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
