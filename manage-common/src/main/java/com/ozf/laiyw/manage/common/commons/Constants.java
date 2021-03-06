package com.ozf.laiyw.manage.common.commons;

public class Constants {

    public static final String LOGIN_VERIFICATION_CODE_PREFIX = "login_verification_code_";
    public static final String FORGETPWD_VERIFICATION_CODE_PREFIX = "forgetpwd_verification_code_";

    public static final String LOGIN_RULE = "LOGIN_RULE";
    public static final String EMAIL_SERVER = "EMAIL_SERVER";

    public static final Long ONE_MINUTE = 1000 * 60L;

    public static final String UTF_8 = "UTF-8";
    public static final String USER_AGENT = "User-Agent";

    public static final String SESSION_USER = "user";

    //超级管理员账号
    public static final String SUPER_USER_ACCOUNT = "admin";

    // 基本响应信息配置
    public static final String SUCCESS_MESSAGE = "操作成功！";
    public static final String ERROR_MESSAGE_NETWORK_ANOMALY = "网络异常，请稍后重试！";
    public static final String ERROR_MESSAGE_LACK_OF_PARAMETER = "请求参数不足！";
    public static final String ERROR_MESSAGE_INVALID_VOUCHER = "凭证失效，请重新登录！";
    public static final String INCORRECTCREDENTIALSEXCEPTION = "验证未通过,错误的凭证！";
}
