package com.ozf.laiyw.manage.model.vo;

import com.ozf.laiyw.manage.model.Role;
import com.ozf.laiyw.manage.model.User;

/**
 * 用户-角色
 */
public class UserRole {

    private User user;
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
