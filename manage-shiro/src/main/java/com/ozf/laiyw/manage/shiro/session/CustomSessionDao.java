package com.ozf.laiyw.manage.shiro.session;

import com.ozf.laiyw.manage.common.utils.SerializeUtil;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Component
public class CustomSessionDao extends AbstractSessionDAO {

    @Value("${session.shareSessionMapCache}")
    private String shareSessionMapCache;
    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        System.out.println("CustomSessionDao.缓存session：" + sessionId);
        redisCacheUtils.addMapData(shareSessionMapCache, sessionId.toString(), SerializeUtil.serialize(session));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("CustomSessionDao.读取session：" + sessionId);
        String StringSession = (String) redisCacheUtils.getMapDataByKey(shareSessionMapCache, sessionId.toString());
        return SerializeUtil.deserialize(StringSession);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (null == session || null == session.getId()) {
            return;
        }
        System.out.println("CustomSessionDao.更新session：" + session.getId());
        redisCacheUtils.addMapData(shareSessionMapCache, session.getId().toString(), SerializeUtil.serialize(session));
    }

    @Override
    public void delete(Session session) {
        System.out.println("CustomSessionDao.删除session：" + session.getId());
        redisCacheUtils.deleteMapDataByKey(shareSessionMapCache, session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Map<String, String> map = redisCacheUtils.getCacheMap(shareSessionMapCache);
        return SerializeUtil.deserializeList(map.values());
    }
}
