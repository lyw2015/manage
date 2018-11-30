package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.RoleMapper;
import com.ozf.laiyw.manage.model.Role;
import com.ozf.laiyw.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/28 17:28
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> allRole() {
        return roleMapper.allRole();
    }

    @Override
    public PageInfo queryRoles(PageInfo pageInfo, Role role) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(roleMapper.getRoles(role));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public Role findRoleById(String id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public void saveRole(Role role) {
        String roleId = StringUtils.randUUID();
        role.setId(roleId);
        role.setUpdateTime(DateUtils.getDateTime());
        roleMapper.saveRole(role);
        if (StringUtils.isNotEmpty(role.getMenuIds())) {
            roleMapper.saveRoleMenu(roleId, Arrays.asList(role.getMenuIds().split(",")));
        }
    }

    @Override
    public void updateRole(Role role) {
        role.setUpdateTime(DateUtils.getDateTime());
        roleMapper.updateRole(role);
        roleMapper.removeRoleMenuByRoleId(role.getId());
        if (StringUtils.isNotEmpty(role.getMenuIds())) {
            roleMapper.saveRoleMenu(role.getId(), Arrays.asList(role.getMenuIds().split(",")));
        }
    }

    @Override
    public int updateRoleStatus(String id) {
        int count = roleMapper.isQuote(id);
        if (count > 0) {
            return -2;
        }
        roleMapper.removeRoleMenuByRoleId(id);
        return roleMapper.updateRoleStatus(id);
    }
}
