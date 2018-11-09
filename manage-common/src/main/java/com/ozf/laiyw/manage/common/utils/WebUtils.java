package com.ozf.laiyw.manage.common.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    public static boolean isAjax(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
            return true;
        }
        return false;
    }
}
