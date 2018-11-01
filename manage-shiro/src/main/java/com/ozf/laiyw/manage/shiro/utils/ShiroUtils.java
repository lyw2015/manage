package com.ozf.laiyw.manage.shiro.utils;

import com.ozf.laiyw.manage.model.User;
import org.apache.shiro.SecurityUtils;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/1 10:42
 */
public class ShiroUtils {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
