package com.ozf.laiyw.manage.shiro.realm;

import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
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
        System.out.println("doGetAuthorizationInfo=================授权：" + account);
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
        System.out.println("doGetAuthenticationInfo=================验证：" + account);
        User user = userService.findByUserAccount(account);
        if (null == user) {
            throw new UnknownAccountException();
        }
        if (Boolean.TRUE.equals(user.getLocked() == 1)) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                getName()
        );
        return authenticationInfo;
    }
}
