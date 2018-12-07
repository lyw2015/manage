package com.ozf.laiyw.manage.web.controller.base;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
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

    @SystemLog(description = "登录/获取验证码")
    @RequestMapping("/getVerificationCode")
    @ResponseBody
    public WebResult getVerificationCode(String email) {
        if (StringUtils.isEmpty(email)) {
            return WebResult.errorResult("无效的邮箱");
        }
        try {
            int count = userService.getVerificationCode(email);
            if (count == -1) {
                return WebResult.errorResult("该邮箱尚未绑定账号");
            }
            return WebResult.successResult("验证码发送成功");
        } catch (Exception e) {
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "忘记密码/获取验证码")
    @RequestMapping("/getVerificationCodeByType")
    @ResponseBody
    public WebResult getVerificationCodeByType(String account, String type) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(type)) {
            return WebResult.errorResult("无效的账号或类型");
        }
        try {
            int count = userService.getVerificationCodeByType(account, type);
            if (count == 0) {
                return WebResult.errorResult("不存在此账号");
            } else if (count == -1) {
                return WebResult.errorResult("此账号未绑定邮箱");
            } else if (count == -2) {
                return WebResult.errorResult("此账号未绑定手机号码");
            } else if (count == -9) {
                return WebResult.errorResult("无效类型");
            }
            return WebResult.successResult("验证码发送成功");
        } catch (Exception e) {
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "忘记密码/修改密码")
    @RequestMapping("/forgetPwd")
    @ResponseBody
    public WebResult forgetPwd(String account, String verificationCode, String password) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(verificationCode) || StringUtils.isEmpty(password)) {
            return WebResult.errorResult("账号、验证码、密码不能为空");
        }
        try {
            int count = userService.forgetPwd(account, verificationCode, password);
            if (count == 0) {
                return WebResult.errorResult("不存在此账号");
            } else if (count == -1) {
                return WebResult.errorResult("验证码已失效");
            } else if (count == -2) {
                return WebResult.errorResult("验证码错误");
            }
            return WebResult.successResult("密码修改成功");
        } catch (Exception e) {
            return WebResult.errorNetworkAnomalyResult();
        }
    }

}
