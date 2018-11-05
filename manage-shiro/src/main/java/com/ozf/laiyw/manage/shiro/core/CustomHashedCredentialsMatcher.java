package com.ozf.laiyw.manage.shiro.core;

import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/5 22:31
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${shiro.login.number.validate}")
    private Boolean validate;
    @Value("${shiro.error.number}")
    private Integer number;
    @Value("${shiro.again.login}")
    private Integer again;

    public CustomHashedCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (!validate) {
            return super.doCredentialsMatch(token, info);
        }
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        String loginDeviceType = customUsernamePasswordToken.getLoginDeviceType();
        String userAccount = customUsernamePasswordToken.getUsername();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Integer value = (Integer) redisCacheUtils.getCacheObject(userAccount + loginDeviceType);
        if (null != value) {
            atomicInteger = new AtomicInteger(value);
        }
        if (atomicInteger.get() >= number) {
            throw new ExcessiveAttemptsException("验证未通过,错误次数已达" + number + "次，请" + again + "分钟后重试");
        }
        boolean matches = super.doCredentialsMatch(customUsernamePasswordToken, info);
        if (matches) {
            redisCacheUtils.delete(userAccount + loginDeviceType);
        } else {
            redisCacheUtils.setCacheObject(userAccount + loginDeviceType, atomicInteger.incrementAndGet(), again, TimeUnit.MINUTES);
        }
        return matches;
    }
}
