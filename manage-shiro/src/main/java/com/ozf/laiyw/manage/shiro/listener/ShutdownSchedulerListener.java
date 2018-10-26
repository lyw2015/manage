package com.ozf.laiyw.manage.shiro.listener;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/25 12:09
 */
@WebListener
public class ShutdownSchedulerListener implements ServletContextListener {

    private final Logger logger = Logger.getLogger("ShutdownSchedulerListener");

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public synchronized void contextDestroyed(ServletContextEvent sce) {
        logger.info("-----------------Shutdown Scheduler Start-----------------");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.shutdown(true);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Shutdown Scheduler Error", e);
        } finally {
            logger.info("-----------------Shutdown Scheduler End-----------------");
        }
    }
}
