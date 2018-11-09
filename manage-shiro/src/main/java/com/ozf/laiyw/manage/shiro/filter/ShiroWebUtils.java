package com.ozf.laiyw.manage.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.common.commons.WebResult;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ShiroWebUtils {

    public static void ajaxResult(ServletResponse response, int code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(new WebResult(false, code, message)));
    }

    public static boolean isAjax(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
            return true;
        }
        return false;
    }
}
