package com.ozf.laiyw.manage.rabbitmq.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息发送到Broker后触发回调，确认消息是否到达Broker服务器，也就是只确认是否正确到达Exchange中
 * @Auther: Laiyw
 * @Date: 2018/10/12 11:13
 */
@Service
public class SendConfirmCallbackListener implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("------------ConfirmCallbackListener------------");
        System.out.println("    correlationData: " + correlationData);
        System.out.println("    ack: " + b);
        System.out.println("    cause: " + s);
    }
}
