package com.ozf.laiyw.manage.model;

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
public class User implements Serializable {

    @ApiModelProperty(value = "ID", dataType = "String")
    private String id;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;
    @ApiModelProperty(value = "是否锁定", dataType = "Boolean")
    private Boolean locked;

    private Boolean rememberMe = false;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
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
}
