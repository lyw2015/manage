package com.ozf.laiyw.manage.service.utils;

import com.ozf.laiyw.manage.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/1 10:42
 */
@Component
public class ShiroUtils {

    private static String method;
    private static int hashiterations;

    @Value("${shiro.encryption.method}")
    public void setMethod(String method) {
        ShiroUtils.method = method;
    }

    @Value("${shiro.encryption.hashiterations}")
    public void setHashiterations(int hashiterations) {
        ShiroUtils.hashiterations = hashiterations;
    }

    /**
     * 加密
     *
     * @param password 原密码
     * @param salt     盐值
     * @return
     */
    public static String getHashPassword(String password, String salt) {
        return new SimpleHash(method, password, ByteSource.Util.bytes(salt), hashiterations).toString();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    /**
     * 修改Shiro中的用户信息
     *
     * @param user
     */
    public static void refreshUser(User user) {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
