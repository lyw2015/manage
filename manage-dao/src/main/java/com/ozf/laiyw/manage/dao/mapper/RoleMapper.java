package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/28 17:14
 */
public interface RoleMapper {

    List<Role> allRole();

    Role findRoleById(String id);

    int updateRoleStatus(String id);

    void removeRoleMenuByRoleId(String roleId);

    void saveRoleMenu(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds);

    int saveRole(Role role);

    int updateRole(Role role);

    List<Role> getRoles(Role role);

    int isQuote(String id);
}
