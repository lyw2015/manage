package com.ozf.laiyw.manage.service.impl.queue;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.service.LoginRecordService;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/31 16:18
 */
@Service
public class LoginRecordQueueService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private LoginRecordService loginRecordService;

    @RabbitListener(queues = "loginRecordQueue")
    public void onMessage(LoginRecord loginRecord) {
        logger.debug("更新操作记录--->" + JSON.toJSONString(loginRecord));
        loginRecordService.updateLoginRecord(loginRecord);
    }
}
