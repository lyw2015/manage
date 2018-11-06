package com.ozf.laiyw.manage.shiro.matcher;

import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.utils.SystemConfig;
import com.ozf.laiyw.manage.shiro.core.CustomUsernamePasswordToken;
import com.ozf.laiyw.manage.shiro.exception.ExcessiveOnlineSingleAccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description: 单账号在线数
 * @Auther: Laiyw
 * @Date: 2018/11/6 15:10
 */
public class SingleAccountHashedCredentialsMatcher extends ErrorNumberHashedCredentialsMatcher {

    @Autowired
    private UserService userService;

    public SingleAccountHashedCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (!SystemConfig.getSaovalidate() || Constants.SUPER_USER_ACCOUNT.equals(((CustomUsernamePasswordToken) token).getUsername())) {
            return super.doCredentialsMatch(token, info);
        }
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        boolean matches = super.doCredentialsMatch(customUsernamePasswordToken, info);
        if (matches) {
            List<LoginRecord> recordList = userService.getOnlineUserByAccount(customUsernamePasswordToken.getUsername());
            if (recordList.size() >= SystemConfig.getSaonumber()) {
                throw new ExcessiveOnlineSingleAccountException("验证未通过,当前账号在线数已达上限");
            }
        }
        return matches;
    }
}
