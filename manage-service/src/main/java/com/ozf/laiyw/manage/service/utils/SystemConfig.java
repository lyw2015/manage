package com.ozf.laiyw.manage.service.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ozf.laiyw.manage.common.commons.Constants;

import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 20:06
 */
public class SystemConfig {

    //是否开启单用户在线数校验
    private static Boolean saovalidate;
    //单个账号最多可在线数
    private static Integer saonumber;
    //是否启用登录错误次数验证
    private static Boolean lenvalidate;
    //错误多少次后锁定账号
    private static Integer errornumber;
    //超过错误次数锁定账号时间
    private static Integer againlogin;

    public static void set(Map<String, String> map) {
        loginRule(JSON.parseObject(map.get(Constants.LOGIN_RULE)));
    }

    public static void loginRule(JSONObject jsonObject) {
        setSaovalidate(jsonObject.getBoolean("saovalidate"));
        setSaonumber(jsonObject.getInteger("saonumber"));
        setLenvalidate(jsonObject.getBoolean("lenvalidate"));
        setErrornumber(jsonObject.getInteger("errornumber"));
        setAgainlogin(jsonObject.getInteger("againlogin"));
    }

    public static Boolean getSaovalidate() {
        return saovalidate;
    }

    public static void setSaovalidate(Boolean saovalidate) {
        SystemConfig.saovalidate = saovalidate;
    }

    public static Integer getSaonumber() {
        return saonumber;
    }

    public static void setSaonumber(Integer saonumber) {
        SystemConfig.saonumber = saonumber;
    }

    public static Boolean getLenvalidate() {
        return lenvalidate;
    }

    public static void setLenvalidate(Boolean lenvalidate) {
        SystemConfig.lenvalidate = lenvalidate;
    }

    public static Integer getErrornumber() {
        return errornumber;
    }

    public static void setErrornumber(Integer errornumber) {
        SystemConfig.errornumber = errornumber;
    }

    public static Integer getAgainlogin() {
        return againlogin;
    }

    public static void setAgainlogin(Integer againlogin) {
        SystemConfig.againlogin = againlogin;
    }
}
