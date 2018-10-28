package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.model.User;
import org.apache.shiro.SecurityUtils;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/26 18:41
 */
public class BaseController {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    protected User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
