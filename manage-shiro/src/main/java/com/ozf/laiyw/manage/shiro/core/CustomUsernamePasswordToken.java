package com.ozf.laiyw.manage.shiro.core;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/5 22:34
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    private String loginDeviceType;

    public CustomUsernamePasswordToken(String username, String password, boolean rememberMe, String loginDeviceType) {
        super(username, password, rememberMe);
        this.loginDeviceType = loginDeviceType;
    }

    public String getLoginDeviceType() {
        return loginDeviceType;
    }

    public void setLoginDeviceType(String loginDeviceType) {
        this.loginDeviceType = loginDeviceType;
    }
}
