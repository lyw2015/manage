package com.ozf.laiyw.manage.web.controller;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.web.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/29 19:34
 */
@SystemLog(description = "用户管理")
@RequestMapping("/user")
@Controller
public class UserController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    //校验邮箱是否已绑定账号
    @RequestMapping("/checkEmail")
    @ResponseBody
    public WebResult checkEmail(String email) {
        if (null == userService.getUserByEmail(email)) {
            return WebResult.successResult(false);
        }
        return WebResult.successResult(true);
    }

    @SystemLog(description = "查看用户列表")
    @RequestMapping("/queryUser")
    @ResponseBody
    public PageInfo queryUser(PageInfo pageInfo, User user) {
        return userService.queryUser(pageInfo, user);
    }

    @SystemLog(description = "添加用户")
    @RequestMapping("/saveUser")
    @ResponseBody
    public WebResult saveUser(User user) {
        if (StringUtils.isEmpty(user.getOrganizationId())
                || StringUtils.isEmpty(user.getType())
                || StringUtils.isEmpty(user.getAccount())
                || StringUtils.isEmpty(user.getUsername())) {
            return WebResult.errorResult("所属机构、用户类型、登录账号、用户名称不能为空");
        }
        try {
            int count = userService.saveUser(user);
            if (count == -1) {
                return WebResult.errorResult("添加失败，登录账号已存在");
            } else if (count == -2) {
                return WebResult.errorResult("添加失败，该邮箱已被其他账号绑定");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("添加用户错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改用户")
    @RequestMapping("/updateUser")
    @ResponseBody
    public WebResult updateUser(User user) {
        if (StringUtils.isEmpty(user.getOrganizationId())
                || StringUtils.isEmpty(user.getType())
                || StringUtils.isEmpty(user.getAccount())
                || StringUtils.isEmpty(user.getUsername())) {
            return WebResult.errorResult("所属机构、用户类型、登录账号、用户名称不能为空");
        }
        if (StringUtils.isEmpty(user.getId())) {
            return WebResult.errorResult("无效用户");
        }
        try {
            int count = userService.updateUser(user);
            if (count == -2) {
                return WebResult.errorResult("修改失败，该邮箱已被其他账号绑定");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改用户错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "查看用户详情")
    @RequestMapping("/getUserById")
    @ResponseBody
    public WebResult getUserById(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效用户");
        }
        try {
            return WebResult.successResult(userService.getUserById(id));
        } catch (Exception e) {
            logger.error("查看用户详情错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "停用用户")
    @RequestMapping("/disableUser")
    @ResponseBody
    public WebResult disableUser(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效用户");
        }
        try {
            int count = userService.updateUserStatus(id, 3);
            if (count == -1) {
                return WebResult.errorResult("停用错误，超级管理员账户不可执行此操作");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("停用用户错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "启用用户")
    @RequestMapping("/enableUser")
    @ResponseBody
    public WebResult enableUser(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效用户");
        }
        try {
            int count = userService.updateUserStatus(id, 1);
            if (count == -1) {
                return WebResult.errorResult("启用错误，超级管理员账户不可执行此操作");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("启用用户错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "删除用户")
    @RequestMapping("/removeUser")
    @ResponseBody
    public WebResult removeUser(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效用户");
        }
        try {
            int count = userService.updateUserStatus(id, 0);
            if (count == -1) {
                return WebResult.errorResult("删除错误，超级管理员账户不可执行此操作");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("删除用户错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "重置用户密码")
    @RequestMapping("/resetUserPwd")
    @ResponseBody
    public WebResult resetUserPwd(String id) {
        if (StringUtils.isEmpty(id)) {
            return WebResult.errorResult("无效用户");
        }
        try {
            userService.resetUserPwd(id);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("重置用户密码错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

}
