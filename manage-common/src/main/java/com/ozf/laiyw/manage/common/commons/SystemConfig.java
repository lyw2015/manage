package com.ozf.laiyw.manage.common.commons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 20:06
 */
public class SystemConfig {

    private static Map<String, String> dataMap = new HashMap<>();

    //----------------------------登录规则
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
    //----------------------------邮箱服务
    //服务器地址
    private static String host;
    //端口
    private static Integer port;
    //发送人邮箱地址
    private static String username;
    //发送人邮箱授权码
    private static String password;

    public static void set(Map<String, String> map) {
        setDataMap(map);
        loginRule(JSON.parseObject(map.get(Constants.LOGIN_RULE)));
        emailServer(JSON.parseObject(map.get(Constants.EMAIL_SERVER)));
    }

    public static void emailServer(JSONObject jsonObject) {
        setHost(jsonObject.getString("host"));
        setPort(jsonObject.getInteger("port"));
        setUsername(jsonObject.getString("username"));
        setPassword(jsonObject.getString("password"));
    }

    public static void loginRule(JSONObject jsonObject) {
        setSaovalidate(jsonObject.getBoolean("saovalidate"));
        setSaonumber(jsonObject.getInteger("saonumber"));
        setLenvalidate(jsonObject.getBoolean("lenvalidate"));
        setErrornumber(jsonObject.getInteger("errornumber"));
        setAgainlogin(jsonObject.getInteger("againlogin"));
    }

    public static String getDataByType(String type) {
        return dataMap.get(type);
    }

    public static Map<String, String> getDataMap() {
        return dataMap;
    }

    public static void setDataMap(Map<String, String> dataMap) {
        SystemConfig.dataMap = dataMap;
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

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        SystemConfig.host = host;
    }

    public static Integer getPort() {
        return port;
    }

    public static void setPort(Integer port) {
        SystemConfig.port = port;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SystemConfig.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SystemConfig.password = password;
    }
}
