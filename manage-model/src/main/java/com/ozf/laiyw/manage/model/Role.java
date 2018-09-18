package com.ozf.laiyw.manage.model;

import com.ozf.laiyw.manage.model.vo.RolePermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 角色
 */
@XmlRootElement
@ApiModel(value = "Role", description = "角色")//swagger2
public class Role {

    @ApiModelProperty(value = "ID", dataType = "String")
    private String id;
    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;
    private List<RolePermission> rolePermissionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RolePermission> getRolePermissionList() {
        return rolePermissionList;
    }

    public void setRolePermissionList(List<RolePermission> rolePermissionList) {
        this.rolePermissionList = rolePermissionList;
    }
}
