package com.ozf.laiyw.manage.model.vo;

import com.ozf.laiyw.manage.model.Permission;
import com.ozf.laiyw.manage.model.Role;

/**
 * 角色-权限
 */
public class RolePermission {

    private Role role;
    private Permission permission;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
