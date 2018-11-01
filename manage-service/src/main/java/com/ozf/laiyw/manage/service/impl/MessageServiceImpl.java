package com.ozf.laiyw.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.model.vo.SocketMessage;
import com.ozf.laiyw.manage.service.MessageService;
import com.ozf.laiyw.manage.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/1 10:28
 */
@Transactional
@Service("manageMessageServiceImpl")
public class MessageServiceImpl implements MessageService {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    //获取公共的消息
    @Override
    public SocketMessage getSocketMessage() {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setOnlineuser(userService.countOnline());
        socketMessage.setNewuser(userService.countTodayNewUser());
        socketMessage.setTodayguest(userService.countTodayTuest());
        return socketMessage;
    }

    @Override
    public void socketSendOffLineMessagesToUser(User user, WebSocketSession webSocketSession) {
        //推送用户信息
        socket(new Message(user), webSocketSession);
        //推送公共信息
        socket(new Message(getSocketMessage()), webSocketSession);
        //推送用户相关的信息
        //to do...
    }

    @Override
    public void socketSendMessageToUsers(Message message, Map<String, WebSocketSession> userSocketSessionMap) {
        if (null == message || null == userSocketSessionMap || userSocketSessionMap.isEmpty()) {
            return;
        }
        //不指定推送用户则群发
        if (null == message.getToUserIds() || message.getToUserIds().isEmpty()) {
            for (WebSocketSession webSocketSession : userSocketSessionMap.values()) {
                socket(message, webSocketSession);
            }
        } else {
            for (String userId : message.getToUserIds()) {
                List<WebSocketSession> webSocketSessionList = findWebSocketSessionByUserId(userId, userSocketSessionMap);
                for (WebSocketSession webSocketSession : webSocketSessionList) {
                    socket(message, webSocketSession);
                }
            }
        }
    }

    /**
     * 根据用户ID找出所有登录用户
     *
     * @param userId
     * @param userSocketSessionMap
     * @return
     */
    private List<WebSocketSession> findWebSocketSessionByUserId(String userId, Map<String, WebSocketSession> userSocketSessionMap) {
        List<WebSocketSession> list = new ArrayList<>();
        for (String key : userSocketSessionMap.keySet()) {
            if (key.startsWith(userId)) {
                list.add(userSocketSessionMap.get(key));
            }
        }
        return list;
    }

    private boolean socket(final Message message, final WebSocketSession webSocketSession) {
        try {
            if (null != webSocketSession && webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(message)));
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error(JSON.toJSONString(message));
            logger.error("push socket message error", e);
            return false;
        }
    }
}
