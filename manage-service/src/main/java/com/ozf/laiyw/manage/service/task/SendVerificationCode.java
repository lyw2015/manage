package com.ozf.laiyw.manage.service.task;

import com.ozf.laiyw.manage.common.utils.EmailUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.vo.VerificationCode;
import com.ozf.laiyw.manage.service.task.commons.AbstractTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/12/7 11:18
 */
@Component
public class SendVerificationCode extends AbstractTask {

    @Value("${verificationCode.effective.time}")
    private Integer vcetime;

    @Value("${redis.verification.code.queue.key}")
    @Override
    public void setQueueKey(String queueKey) {
        super.setQueueKey(queueKey);
    }

    @Override
    public void execute(Object object) throws Exception {
        VerificationCode vc = (VerificationCode) object;
        vc.setVerificationCode(StringUtils.randomCode(6));
        if (VerificationCode.SEND_TYPE_EMAIL.equals(vc.getType())) {
            email(vc);
        } else if (VerificationCode.SEND_TYPE_PHONE.equals(vc.getType())) {
            phone(vc);
        } else {
            logger.error("无效的类型--->" + vc.getType());
        }
    }

    private void saveCacheCode(VerificationCode vc) {
        redisCacheUtils.setCacheObject(vc.getPrefix() + vc.getAccount(), vc.getVerificationCode(), vcetime, TimeUnit.MINUTES);
    }

    public void email(VerificationCode vc) {
        try {
            EmailUtils.send(vc.getEmail(), vc.getVerificationCode(), vcetime);
            saveCacheCode(vc);
        } catch (Exception e) {
            logger.error("邮箱验证码发送错误", e);
        }
    }

    public void phone(VerificationCode vc) {
        try {
            //TODO 发送短信验证码
            saveCacheCode(vc);
        } catch (Exception e) {
            logger.error("手机验证码发送错误", e);
        }
    }
}
