package com.ozf.laiyw.manage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozf.laiyw.manage.model.vo.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@XmlRootElement//cxf
@ApiModel(value = "User", description = "用户")//swagger2
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    @ApiModelProperty(value = "ID", dataType = "String")
    @JsonIgnore
    private String id;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;
    @ApiModelProperty(value = "登录账号", dataType = "String")
    private String account;
    @ApiModelProperty(value = "密码", dataType = "String")
    @JsonIgnore
    private String password;
    @ApiModelProperty(value = "邮箱", dataType = "String")
    private String mailbox;
    @ApiModelProperty(value = "电话号码", dataType = "String")
    private String phone;
    @ApiModelProperty(value = "性别", dataType = "String")
    private String sex;
    @ApiModelProperty(value = "是否锁定", dataType = "int")
    private int locked;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createDate;
    @ApiModelProperty(value = "头像", dataType = "String")
    private String headImg;

    private Boolean rememberMe = false;
    private String verificationCode;
    private List<UserRole> userRoleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
