package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.dao.mapper.TestMapper;
import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.rabbitmq.producer.MessageService;
import com.ozf.laiyw.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/15 17:40
 */
@Transactional
@Service
public class TestMessageTxImpl implements TestService {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private MessageService messageService;

    @Override
    public String testFind() {
        String context = testMapper.testFind();
        messageService.sendMessage(new Message(context), "log");
        return context;
    }
}
