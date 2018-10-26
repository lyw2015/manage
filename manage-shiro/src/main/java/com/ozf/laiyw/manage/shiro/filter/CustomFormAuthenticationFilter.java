package com.ozf.laiyw.manage.shiro.filter;

import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private final String sessionUser = "user";
    @Autowired
    private UserService userService;

    /**
     * 可用于验证码判断
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("自定义--FormAuthenticationFilter...onAccessDenied");
        return super.onAccessDenied(request, response);
    }

    /**
     * 记住我功能
     * 该方法只作用于一般请求 也就是权限为user的，而访问不了权限为authc
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        //如果 isAuthenticated 为 false 证明不是登录过的，同时 isRememberd 为true 证明是没登陆直接通过记住我功能进来的
        if (!subject.isAuthenticated() && subject.isRemembered() && null == session.getAttribute(sessionUser)) {
            Object principal = subject.getPrincipal();
            if (null != principal) {
                User user = userService.findByUserName(principal.toString());
                if (null != user) {
                    session.setAttribute(sessionUser, user);
                }
            }
        }
        return subject.isAuthenticated() || subject.isRemembered();
    }
}
