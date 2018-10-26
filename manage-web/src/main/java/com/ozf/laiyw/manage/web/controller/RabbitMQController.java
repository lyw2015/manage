package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/11 16:03
 */
@RequestMapping("/mq")
@Controller
public class RabbitMQController {

    @Autowired
    @Qualifier("testMessageTxImpl")
    private TestService testService;

    @RequestMapping("/send")
    @ResponseBody
    public Object send() {
        return testService.testFind();
    }
}
