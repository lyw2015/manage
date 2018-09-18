package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录
 */
@RequestMapping("/manage")
@Controller
public class LoginController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    @ResponseBody
    public WebResult login(User user) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRememberMe()
            );
            SecurityUtils.getSubject().login(token);
            return WebResult.successResult("登录成功");
        } catch (UnknownAccountException uae) {
            return WebResult.errorResult("验证未通过,未知账户");
        } catch (IncorrectCredentialsException ice) {
            return WebResult.errorResult("验证未通过,错误的凭证");
        } catch (LockedAccountException lae) {
            return WebResult.errorResult("验证未通过,账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return WebResult.errorResult("验证未通过,错误次数过多");
        }
    }
}