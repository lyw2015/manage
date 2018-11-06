package com.ozf.laiyw.manage.shiro.realm;

import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
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
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = ((UsernamePasswordToken) token).getUsername();
        User user = userService.findByUserAccount(account);
        if (null == user) {
            throw new UnknownAccountException("验证未通过,未知账户");
        }
        if (Boolean.TRUE.equals(user.getLocked() == 1)) {
            throw new LockedAccountException("验证未通过,账户已锁定");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getAccount()),
                getName()
        );
        return authenticationInfo;
    }
}
