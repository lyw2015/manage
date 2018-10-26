package com.ozf.laiyw.manage.rabbitmq.producer.impl;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.rabbitmq.producer.MessageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/11 15:57
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMessage(Object message) {
        amqpTemplate.convertAndSend(JSON.toJSONString(message).getBytes());
    }

    @Override
    public void sendMessage(Object message, String routingKey) {
        amqpTemplate.convertAndSend(routingKey, JSON.toJSONString(message).getBytes());
    }

    @Override
    public void sendMessage(Object message, String key, String value) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(key, value);
        amqpTemplate.send(new Message(JSON.toJSONString(message).getBytes(), messageProperties));
    }
}
