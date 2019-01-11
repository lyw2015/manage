package com.ozf.laiyw.manage.web.controller.base;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.AddressUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.LoginRecordService;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import com.ozf.laiyw.manage.shiro.core.CustomUsernamePasswordToken;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private LoginRecordService loginRecordService;
    @Value("#{${shiro.session.effective.time}}")
    private Long sessionTime;
    @Value("#{${shiro.rememberme.cookie.maxage}}")
    private Long cookieMaxAge;

    @SystemLog(description = "验证码登录")
    @RequestMapping("/verificationCodeLogin")
    @ResponseBody
    public WebResult verificationCodeLogin(User user) {
        String verificationCode = user.getVerificationCode();
        if (StringUtils.isEmpty(user.getMailbox()) || StringUtils.isEmpty(verificationCode)) {
            return WebResult.errorResult("邮箱、验证码不能为空");
        }
        user = userService.getUserByEmail(user.getMailbox());
        if (null == user) {
            return WebResult.errorResult("无效邮箱");
        }
        int count = userService.checkVerificationCode(Constants.LOGIN_VERIFICATION_CODE_PREFIX, user.getAccount(), verificationCode);
        if (count == -1) {
            return WebResult.errorResult("验证码已失效");
        } else if (count == -2) {
            return WebResult.errorResult("验证码错误");
        }
        return executeLogin(new CustomUsernamePasswordToken(user.getMailbox(), user.getRememberMe()));
    }

    @SystemLog(description = "账号密码登录")
    @RequestMapping("/login")
    @ResponseBody
    public WebResult login(User user) {
        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())) {
            return WebResult.errorResult("账号密码不能为空");
        }
        return executeLogin(new CustomUsernamePasswordToken(
                user.getAccount(),
                user.getPassword(),
                user.getRememberMe()
        ));
    }

    //执行登录
    private WebResult executeLogin(UsernamePasswordToken token) {
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(Constants.USER_AGENT));
            ShiroUtils.getSubject().login(token);
            if (token.isRememberMe()) {
                SecurityUtils.getSubject().getSession().setTimeout(cookieMaxAge);
            } else {
                SecurityUtils.getSubject().getSession().setTimeout(sessionTime);
            }
            loginRecordService.saveLoginRecord(userAgent, AddressUtils.getIpAddress(request));
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
        redirect("/login.html");
    }
}
