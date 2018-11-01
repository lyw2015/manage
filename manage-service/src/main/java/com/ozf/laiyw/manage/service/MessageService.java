package com.ozf.laiyw.manage.service;

import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.model.vo.SocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/1 10:22
 */
public interface MessageService {

    /**
     * 获取公共的消息
     *
     * @return
     */
    SocketMessage getSocketMessage();

    /**
     * 推送离线消息
     *
     * @param user
     * @param webSocketSession
     */
    void socketSendOffLineMessagesToUser(User user, WebSocketSession webSocketSession);

    /**
     * 推送消息
     *
     * @param message
     * @param userSocketSessionMap
     */
    void socketSendMessageToUsers(Message message, Map<String, WebSocketSession> userSocketSessionMap);
}
