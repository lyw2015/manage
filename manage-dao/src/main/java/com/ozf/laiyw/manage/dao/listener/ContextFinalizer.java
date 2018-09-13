package com.ozf.laiyw.manage.dao.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 关闭数据库驱动
 */
@WebListener
public class ContextFinalizer implements ServletContextListener {

    private final Logger logger = Logger.getLogger("ContextFinalizer");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        try {
            logger.info("=====================Deregistered Driver Start============================");
            while (drivers.hasMoreElements()) {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                logger.info("===Driver " + driver + " Deregistered");
            }
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Deregistering Driver " + driver, ex);
        } finally {
            try {
                AbandonedConnectionCleanupThread.shutdown();
                logger.info("=====================Deregistered Driver End============================");
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Server problem cleaning up", e);
            }
        }
    }
}
