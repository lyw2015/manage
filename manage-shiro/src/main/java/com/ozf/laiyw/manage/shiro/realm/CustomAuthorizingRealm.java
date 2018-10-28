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
        String userName = ((User) principals.getPrimaryPrincipal()).getUsername();
        System.out.println("doGetAuthorizationInfo=================授权：" + userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(userService.findRolesByUserName(userName));
        simpleAuthorizationInfo.addStringPermissions(userService.findPermissionsByUserName(userName));
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
        String userName = ((UsernamePasswordToken) token).getUsername();
        System.out.println("doGetAuthenticationInfo=================验证：" + userName);
        User user = userService.findByUserName(userName);
        if (null == user) {
            throw new UnknownAccountException();
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
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
