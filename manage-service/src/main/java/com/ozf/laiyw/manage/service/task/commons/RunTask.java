package com.ozf.laiyw.manage.service.task.commons;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/12/5 16:49
 */
@Component
public class RunTask implements InitializingBean, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private ThreadPoolTaskExecutor executor;
    private Map<String, AbstractTask> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == map || map.isEmpty()) {
            return;
        }
        for (String taskName : map.keySet()) {
            try {
                executor.execute(map.get(taskName));
                logger.info("任务[" + taskName + "]已启动");
            } catch (Exception e) {
                logger.error("任务[" + taskName + "]启动失败", e);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        map = applicationContext.getBeansOfType(AbstractTask.class);
    }
}
