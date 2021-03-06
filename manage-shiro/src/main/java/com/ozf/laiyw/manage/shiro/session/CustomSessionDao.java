package com.ozf.laiyw.manage.shiro.session;

import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.SerializeUtil;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Component
public class CustomSessionDao extends AbstractSessionDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Value("${session.shareSessionMapCache}")
    private String shareSessionMapCache;
    @Value("#{${shiro.session.effective.time}}")
    private Integer effectiveTime;
    @Value("${redis.login.record.queue.key}")
    private String loginReocrdQueueKey;
    @Autowired
    private RedisCacheUtils redisCacheUtils;

    //如DefaultSessionManager在创建完session后会调用该方法；如保存到关系数据库/文件系统/NoSQL数据库；
    // 即可以实现会话的持久化；返回会话ID；主要此处返回的ID.equals(session.getId())；
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        redisCacheUtils.addMapData(shareSessionMapCache, sessionId.toString(), SerializeUtil.serialize(session));
        return sessionId;
    }

    //根据会话ID获取会话
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String stringSession = (String) redisCacheUtils.getMapDataByKey(shareSessionMapCache, sessionId.toString());
        if (StringUtils.isEmpty(stringSession)) {
            return null;
        }
        Session session = SerializeUtil.deserialize(stringSession);
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return null;
        }
        return session;
    }

    //更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
    @Override
    public void update(Session session) throws UnknownSessionException {
        //如果会话过期/停止 没必要再更新了
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        redisCacheUtils.addMapData(shareSessionMapCache, session.getId().toString(), SerializeUtil.serialize(session));
        if (isChange()) {
            updateLoginRecord(session, false);
        }
    }

    private boolean isChange() {
        User user = ShiroUtils.getCurrentUser();
        if (null != user) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String requestUri = request.getRequestURI();
            if (requestUri.contains("/statics/") || requestUri.contains("/plugins/")) {
                return false;
            }
            return true;
        }
        return false;
    }

    //删除会话；当会话过期/会话停止（如用户退出时）会调用
    @Override
    public void delete(Session session) {
        redisCacheUtils.deleteMapDataByKey(shareSessionMapCache, session.getId().toString());
        updateLoginRecord(session, true);
    }

    //获取当前所有活跃用户，如果用户量多此方法影响性能
    @Override
    public Collection<Session> getActiveSessions() {
        Map<String, String> map = redisCacheUtils.getCacheMap(shareSessionMapCache);
        return SerializeUtil.deserializeList(map.values());
    }

    private void updateLoginRecord(Session session, boolean isDelete) {
        try {
            LoginRecord loginRecord = new LoginRecord();
            if (isDelete) {//退出
                loginRecord.setOnline(Boolean.FALSE.toString());
                Date logoutDate = new Date();
                //session过期时间
                Date invalidDate = new Date(session.getLastAccessTime().getTime() + effectiveTime);
                if (invalidDate.before(logoutDate)) {
                    logoutDate = invalidDate;
                }
                loginRecord.setLogoutTime(DateUtils.formatDateTime(logoutDate));
            }
            loginRecord.setSessionId(session.getId().toString());
            loginRecord.setLastTime(DateUtils.formatDateTime(session.getLastAccessTime()));//最后操作时间
            redisCacheUtils.leftPush(loginReocrdQueueKey, loginRecord);
        } catch (Exception e) {
            logger.error("登录日志数据入栈失败，队列名称：" + loginReocrdQueueKey, e);
        }
    }

}
