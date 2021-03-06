package com.ozf.laiyw.manage.shiro.matcher;

import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.commons.SystemConfig;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 登录错误次数验证
 * @Auther: Laiyw
 * @Date: 2018/11/5 22:31
 */
public class ErrorNumberHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisCacheUtils redisCacheUtils;
    private final String PREFIX = "login_error_cock_user_";

    public ErrorNumberHashedCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        User user = (User) info.getPrincipals().getPrimaryPrincipal();
        if (!SystemConfig.getLenvalidate() || Constants.SUPER_USER_ACCOUNT.equals(user.getAccount())) {
            return super.doCredentialsMatch(token, info);
        }
        String key = PREFIX + user.getUsername();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Integer value = (Integer) redisCacheUtils.getCacheObject(key);
        if (null != value) {
            atomicInteger = new AtomicInteger(value);
        }
        if (atomicInteger.get() >= SystemConfig.getErrornumber()) {
            throw new ExcessiveAttemptsException("验证未通过,错误次数已达" + SystemConfig.getErrornumber() + "次，请" + SystemConfig.getAgainlogin() + "分钟后重试");
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            redisCacheUtils.delete(key);
        } else {
            redisCacheUtils.setCacheObject(key, atomicInteger.incrementAndGet(), SystemConfig.getAgainlogin(), TimeUnit.MINUTES);
        }
        return matches;
    }
}
