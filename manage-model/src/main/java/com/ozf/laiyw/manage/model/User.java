package com.ozf.laiyw.manage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement//cxf
@ApiModel(value = "User", description = "用户")//swagger2
public class User implements Serializable {

    @ApiModelProperty(value = "ID", dataType = "String")
    private String id;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String userName;
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
