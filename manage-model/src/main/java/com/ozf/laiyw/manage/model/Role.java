package com.ozf.laiyw.manage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 角色
 */
@XmlRootElement
@ApiModel(value = "Role", description = "角色")
public class Role {

    @ApiModelProperty(value = "ID", dataType = "String")
    private String id;
    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "类型ID", dataType = "String")
    private String type;
    @ApiModelProperty(value = "类型名称", dataType = "String")
    private String typeName;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;
    private Integer status;//默认为1

    private String menuIds;
    private List<Menu> menuList;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
