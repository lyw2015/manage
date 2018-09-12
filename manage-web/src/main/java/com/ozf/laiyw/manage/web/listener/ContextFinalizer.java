package com.ozf.laiyw.manage.web.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 关闭数据库驱动
 */
@WebListener
public class ContextFinalizer implements ServletContextListener {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        try {
            while (drivers.hasMoreElements()) {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                logger.info("Driver " + driver + " Deregistered");
            }
        } catch (SQLException ex) {
            logger.error("Deregistering Driver " + driver, ex);
        } finally {
            try {
                AbandonedConnectionCleanupThread.shutdown();
            } catch (InterruptedException e) {
                logger.error("Server problem cleaning up", e);
            }
        }

    }
}
