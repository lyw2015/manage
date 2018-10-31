package com.ozf.laiyw.manage.service.socket;

import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.model.User;
import org.apache.log4j.Logger;
import org.springframework.web.socket.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/31 22:37
 */
public class SpringWebSocketHandler implements WebSocketHandler {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final String separator = "_";
    private static Map<String, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>();
    }

    //连接成功时触发
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        User user = ((User) webSocketSession.getAttributes().get(Constants.SESSION_USER));
        String key = user.getId() + separator + webSocketSession.getId();
        if (userSocketSessionMap.get(key) == null) {
            userSocketSessionMap.put(key, webSocketSession);
        }
        //当用户登录后，把离线消息推送给用户
        webSocketSession.sendMessage(new TextMessage(DateUtils.getDateTime()));
    }

    //关闭连接时触发
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        closeWebSocketSession(webSocketSession);
    }

    //消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> message) throws Exception {
        if (message.getPayloadLength() == 0)
            return;
        logger.info("WebSocket接收消息--->" + message.getPayload().toString());
    }

    //消息传输错误处理
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable exception) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        closeWebSocketSession(webSocketSession);
    }

    private void closeWebSocketSession(WebSocketSession webSocketSession) {
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        Map.Entry<String, WebSocketSession> entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getValue().getId().equals(webSocketSession.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                break;
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
