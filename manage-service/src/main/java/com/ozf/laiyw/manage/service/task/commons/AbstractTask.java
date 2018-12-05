package com.ozf.laiyw.manage.service.task.commons;

import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/12/5 16:35
 */
public abstract class AbstractTask implements Runnable {

    protected final Logger logger = Logger.getLogger(this.getClass());
    @Value("${redis.right.pop.timeout}")
    private long timeout;
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    protected String queueKey;

    public void setQueueKey(String queueKey) {
        this.queueKey = queueKey;
    }

    @Override
    public void run() {
        Object object = null;
        while (true) {
            object = redisCacheUtils.rightPop(queueKey, timeout, TimeUnit.SECONDS);
            if (null != object) {
                try {
                    execute(object);
                } catch (Exception e) {
                    logger.error("队列名称--->" + queueKey);
                    logger.error("操作数据--->" + object);
                    logger.error("任务异常", e);
                    redisCacheUtils.leftPush(queueKey, object);
                }
            }
        }
    }

    public abstract void execute(Object object) throws Exception;
}
