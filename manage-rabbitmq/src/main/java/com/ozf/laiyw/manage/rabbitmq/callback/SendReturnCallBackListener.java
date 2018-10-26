package com.ozf.laiyw.manage.rabbitmq.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description: 失败后return回调，比如路由不到队列时触发回调
 * @Auther: Laiyw
 * @Date: 2018/10/12 11:17
 */
@Service
public class SendReturnCallBackListener implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("------------ReturnCallBackListener------------");
        System.out.println("    message: " + message);
        System.out.println("    replyCode: " + i);
        System.out.println("    replyText: " + s);
        System.out.println("    exchange: " + s1);
        System.out.println("    routingKey: " + s2);
    }
}
