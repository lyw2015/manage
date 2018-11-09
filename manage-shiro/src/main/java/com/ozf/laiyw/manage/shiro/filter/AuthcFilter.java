package com.ozf.laiyw.manage.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * authc
 */
public class AuthcFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if (ShiroWebUtils.isAjax(request)) {
                ShiroWebUtils.ajaxResult(response, 401, "此功能在当前状态下不可用，请重新登录！");
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }
}
