package com.ozf.laiyw.manage.service.socket;

import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.service.shiro.ShiroUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/31 23:33
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        attributes.put(Constants.SESSION_USER, ShiroUtils.getCurrentUser());
        return true;
    }
}
