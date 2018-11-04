package com.ozf.laiyw.manage.shiro.core;

import com.ozf.laiyw.manage.common.commons.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class ShiroWebSessionManager extends DefaultWebSessionManager {

    @Override
    public void setGlobalSessionTimeout(long globalSessionTimeout) {
        super.setGlobalSessionTimeout(globalSessionTimeout * Constants.ONE_MINUTE);
    }

    @Override
    public void setSessionValidationInterval(long sessionValidationInterval) {
        super.setSessionValidationInterval(sessionValidationInterval * Constants.ONE_MINUTE);
    }

    /**
     * 优化单次请求需要多次访问redis(Session)的问题
     *
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null && null != sessionId) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (sessionObj != null) {
                return (Session) sessionObj;
            }
        }

        Session session = super.retrieveSession(sessionKey);
        if (request != null && null != sessionId) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
