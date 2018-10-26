package com.ozf.laiyw.manage.rabbitmq.producer;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/11 15:56
 */
public interface MessageService {

    void sendMessage(Object message);

    void sendMessage(Object message, String routingKey);

    void sendMessage(Object message, String key, String value);
}
