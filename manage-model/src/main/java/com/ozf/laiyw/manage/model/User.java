package com.ozf.laiyw.manage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@XmlRootElement//cxf
@ApiModel(value = "User", description = "用户")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    @ApiModelProperty(value = "ID", dataType = "String")
    private String id;
    @ApiModelProperty(value = "用户类型", dataType = "String")
    private String type;
    @ApiModelProperty(value = "登录账号", dataType = "String")
    private String account;
    @ApiModelProperty(value = "密码", dataType = "String")
    @JsonIgnore
    private String password;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;
    @ApiModelProperty(value = "性别", dataType = "String")
    private String sex;
    @ApiModelProperty(value = "邮箱", dataType = "String")
    private String mailbox;
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String phone;
    @ApiModelProperty(value = "办公号码", dataType = "String")
    private String officePhone;
    @ApiModelProperty(value = "是否锁定", dataType = "int")
    private Integer status;//默认为1    0：删除  1：正常 2：锁定 3：停用
    @ApiModelProperty(value = "头像", dataType = "String")
    private String headImg;
    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(value = "所属机构", dataType = "String")
    private String organizationId;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;

    private Boolean rememberMe = false;
    private String verificationCode;

    private Organization organization;
    private List<Role> roleList;

    private String roleId;
    private String roleIds;
    private String roleNames;
    private String organizationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
