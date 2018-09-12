package com.ozf.laiyw.manage.app.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ozf.laiyw.manage.app.utils.JwtUtils;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.exception.CustomException;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(jwtUtils.getHeader());
        }
        //token为空
        if (StringUtils.isEmpty(token)) {
            throw new CustomException(Constants.ERROR_MESSAGE_INVALID_VOUCHER);
        }
        //校验token
        DecodedJWT decodedJWT = jwtUtils.verifyToken(token);
        if (null == decodedJWT || jwtUtils.isTokenExpired(decodedJWT.getExpiresAt())) {
            throw new CustomException(Constants.ERROR_MESSAGE_INVALID_VOUCHER);
        }
        return true;
    }
}
