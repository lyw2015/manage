package com.ozf.laiyw.manage.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/12 10:11
 */
@Service
public class LogMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("LogMessageConsumer--->" + message);
    }
}
