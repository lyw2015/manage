package com.ozf.laiyw.manage.shiro.core;

import com.ozf.laiyw.manage.common.commons.Constants;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/8 21:22
 */
public class RememberMeCookie extends SimpleCookie {

    public RememberMeCookie(String name) {
        super(name);
        setMaxAge((int) (Constants.REMEMBERMECOOKIE_MAXAGE / Constants.ONE_MINUTE));
    }

}
