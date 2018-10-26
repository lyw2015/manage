package com.ozf.laiyw.manage.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/12 10:12
 */
@Service
public class OrderMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("OrderMessageConsumer--->" + message);
    }
}
