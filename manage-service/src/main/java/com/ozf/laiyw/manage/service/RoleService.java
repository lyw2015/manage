package com.ozf.laiyw.manage.service;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.Role;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/28 17:28
 */
public interface RoleService {

    List<Role> allRole();

    PageInfo queryRoles(PageInfo pageInfo, Role role);

    Role findRoleById(String id);

    void saveRole(Role role);

    void updateRole(Role role);

    int updateRoleStatus(String id);
}
