package com.ozf.laiyw.manage.shiro.core;

import com.ozf.laiyw.manage.common.utils.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/5 22:34
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    //登录设备类型
    private String loginDeviceType;
    //验证过的账号（邮箱，电话号码）
    private String verificationAccount;

    //-----------------------------验证码登录----------------------------------------------------

    public CustomUsernamePasswordToken(String verificationAccount) {
        this.verificationAccount = verificationAccount;
    }

    public CustomUsernamePasswordToken(String verificationAccount, boolean rememberMe) {
        this(verificationAccount);
        setRememberMe(rememberMe);
    }

    public CustomUsernamePasswordToken(String verificationAccount, boolean rememberMe, String loginDeviceType) {
        this(verificationAccount, rememberMe);
        this.loginDeviceType = loginDeviceType;
    }

    //-----------------------------普通账号密码登录----------------------------------------------------

    public CustomUsernamePasswordToken(String username, String password) {
        super(username, password);
    }

    public CustomUsernamePasswordToken(String username, String password, boolean rememberMe) {
        this(username, password);
        setRememberMe(rememberMe);
    }

    public CustomUsernamePasswordToken(String username, String password, boolean rememberMe, String loginDeviceType) {
        this(username, password, rememberMe);
        this.loginDeviceType = loginDeviceType;
    }

    @Override
    public Object getPrincipal() {
        if (StringUtils.isEmpty(verificationAccount))
            return super.getPrincipal();
        return verificationAccount;
    }

    @Override
    public Object getCredentials() {
        if (StringUtils.isEmpty(verificationAccount))
            return super.getCredentials();
        return verificationAccount;
    }

    public String getVerificationAccount() {
        return verificationAccount;
    }

    public void setVerificationAccount(String verificationAccount) {
        this.verificationAccount = verificationAccount;
    }

    public String getLoginDeviceType() {
        return loginDeviceType;
    }

    public void setLoginDeviceType(String loginDeviceType) {
        this.loginDeviceType = loginDeviceType;
    }
}
