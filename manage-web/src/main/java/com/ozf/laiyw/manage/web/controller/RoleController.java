package com.ozf.laiyw.manage.web.controller;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.Role;
import com.ozf.laiyw.manage.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/28 16:58
 */
@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private RoleService roleService;

    @SystemLog(description = "查看角色列表")
    @RequestMapping("/queryRoles")
    @ResponseBody
    public PageInfo queryRoles(PageInfo pageInfo, Role role) {
        return roleService.queryRoles(pageInfo, role);
    }

    @SystemLog(description = "添加角色")
    @RequestMapping("/addRole")
    @ResponseBody
    public WebResult addRole(Role role) {
        if (StringUtils.isEmpty(role.getName())) {
            return WebResult.errorResult("名称不能为空");
        }
        try {
            roleService.saveRole(role);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("添加角色错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改角色")
    @RequestMapping("/updateRole")
    @ResponseBody
    public WebResult updateRole(Role role) {
        if (StringUtils.isEmpty(role.getName())) {
            return WebResult.errorResult("名称不能为空");
        }
        if (StringUtils.isEmpty(role.getId())) {
            return WebResult.errorResult("无效角色");
        }
        try {
            roleService.updateRole(role);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改角色错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "删除角色")
    @RequestMapping("/updateRoleStatus")
    @ResponseBody
    public WebResult updateRoleStatus(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效角色");
        }
        try {
            int count = roleService.updateRoleStatus(id);
            if (count == -2) {
                return WebResult.errorResult("删除失败，该角色已被引用");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("删除角色错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "查看角色详情")
    @RequestMapping("/findRoleById")
    @ResponseBody
    public WebResult findRoleById(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效角色");
        }
        try {
            return WebResult.successResult(roleService.findRoleById(id));
        } catch (Exception e) {
            logger.error("查看角色详情错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }
}
