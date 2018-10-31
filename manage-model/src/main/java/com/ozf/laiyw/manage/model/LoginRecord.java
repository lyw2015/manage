package com.ozf.laiyw.manage.model;

/**
 * @Description:访问记录
 * @Auther: Laiyw
 * @Date: 2018/10/30 17:54
 */
public class LoginRecord {

    private String id;
    private String sessionId;
    private String userName;//用户名
    private String visitTime;//访问时间（Session创建时间）
    private String lastTime;//最后操作时间
    private String loginTime;//登录时间
    private String logoutTime;//退出时间
    private String onlineTime;//在线时长
    private String online;//是否在线 默认为false
    private String clientIp;//客户端IP
    private String operatingSystemName;//设备名称
    private String browser;//浏览器

    //用于查询
    private String sd;
    private String ed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
        if (null != loginTime && loginTime.contains(" - ")) {
            this.sd = loginTime.split(" - ")[0];
            this.ed = loginTime.split(" - ")[1];
        }
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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
