package com.ozf.laiyw.manage.shiro.realm;

import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import com.ozf.laiyw.manage.shiro.core.CustomUsernamePasswordToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义认证realm
 */
public class CustomAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = ((User) principals.getPrimaryPrincipal()).getAccount();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(userService.findRolesByUserAccount(account));
        simpleAuthorizationInfo.addStringPermissions(userService.findPermissionsByUserAccount(account));
        return simpleAuthorizationInfo;
    }

    /**
     * 验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authenticationToken;
        String account = (String) token.getPrincipal();
        User user = userService.findUserByAccountOrMailbox(account);
        if (null == user) {
            throw new UnknownAccountException("验证未通过,未知账户");
        }
        if (user.getStatus() == 2) {
            throw new LockedAccountException("验证未通过,账户已锁定");
        }
        if (user.getStatus() == 3) {
            throw new LockedAccountException("验证未通过,账户已停用");
        }
        return new SimpleAuthenticationInfo(
                user,
                StringUtils.isNotEmpty(token.getVerificationAccount()) ? ShiroUtils.getHashPassword(account, account) : user.getPassword(),
                ByteSource.Util.bytes(StringUtils.isNotEmpty(token.getVerificationAccount()) ? account : user.getAccount()),
                getName()
        );
    }


}
