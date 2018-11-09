package com.ozf.laiyw.manage.shiro.filter;

import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.utils.AddressUtils;
import com.ozf.laiyw.manage.dao.mapper.UserMapper;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * user
 *
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/8 16:32
 */
public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${session.shareSessionMapCache}")
    private String shareSessionMapCache;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (ShiroWebUtils.isAjax(request)) {
            ShiroWebUtils.ajaxResult(response, 403, Constants.ERROR_MESSAGE_INVALID_VOUCHER);
        } else {
            saveRequestAndRedirectToLogin(request, response);
        }
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            if (!subject.isAuthenticated() && subject.isRemembered()) {
                Session session = subject.getSession();
                session.setTimeout(Constants.REMEMBERMECOOKIE_MAXAGE);
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                final UserAgent userAgent = UserAgent.parseUserAgentString(httpServletRequest.getHeader(Constants.USER_AGENT));
                final LoginRecord exist = userMapper.findLoginRecordByCond(AddressUtils.getIpAddress(httpServletRequest), userAgent.getOperatingSystem().getName(), userAgent.getBrowser().getName());
                if (null != exist && !session.getId().toString().equals(exist.getSessionId())) {
                    redisCacheUtils.deleteMapDataByKey(shareSessionMapCache, exist.getSessionId());
                    userMapper.updateLoginRecordSessionIdBySessionId(session.getId().toString(), exist.getSessionId());
                }
            }
            return subject.isAuthenticated() || subject.isRemembered();
        }
    }
}
