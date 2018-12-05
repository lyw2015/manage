package com.ozf.laiyw.manage.service.task;

import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.service.LoginRecordService;
import com.ozf.laiyw.manage.service.task.commons.AbstractTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/12/5 16:30
 */
@Component
public class UpdateLoginRecord extends AbstractTask {

    @Autowired
    private LoginRecordService loginRecordService;

    @Value("${redis.login.record.queue.key}")
    @Override
    public void setQueueKey(String queueKey) {
        super.setQueueKey(queueKey);
    }

    @Override
    public void execute(Object object) throws Exception {
        LoginRecord loginRecord = (LoginRecord) object;
        loginRecordService.updateLoginRecord(loginRecord);
    }
}
