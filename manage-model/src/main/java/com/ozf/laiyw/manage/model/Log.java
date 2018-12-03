package com.ozf.laiyw.manage.model;

/**
 * 日志
 *
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/27 12:55
 */
public class Log {

    private String id;
    private String clientIp;//客户端IP
    private String deviceType;//设备类型 Computer/Mobile
    private String groupName;//设备分组 Windows/Android
    private String operatingSystemName;//操作系统
    private String browser;//浏览器
    private String agent;//用户代理
    private String requestURI;//请求地址
    private String requestMethod;//请求方法
    private String requestParameter;//请求参数
    private String operationTime;//操作时间
    private String userAccount;//用户账号
    private String operationUsername;//操作用户
    private String responseTime;//响应时间
    private String isError;//是否异常
    private String errorMessage;//异常信息
    private String operationDescription;//操作描述

    //用于查询
    private String sd;
    private String ed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getOperatingSystemName() {
        return operatingSystemName;
    }

    public void setOperatingSystemName(String operatingSystemName) {
        this.operatingSystemName = operatingSystemName;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
        if (null != operationTime && operationTime.contains(" - ")) {
            this.sd = operationTime.split(" - ")[0];
            this.ed = operationTime.split(" - ")[1];
        }
    }

    public String getOperationUsername() {
        return operationUsername;
    }

    public void setOperationUsername(String operationUsername) {
        this.operationUsername = operationUsername;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }
}
