package com.ozf.laiyw.manage.shiro.exception;

import org.apache.shiro.authc.AccountException;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 15:19
 */
public class ExcessiveOnlineSingleAccountException extends AccountException {

    public ExcessiveOnlineSingleAccountException(String message) {
        super(message);
    }

    public ExcessiveOnlineSingleAccountException(Throwable cause) {
        super(cause);
    }

    public ExcessiveOnlineSingleAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
