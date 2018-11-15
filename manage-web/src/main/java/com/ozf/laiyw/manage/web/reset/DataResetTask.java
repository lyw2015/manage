package com.ozf.laiyw.manage.web.reset;

import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/14 11:13
 */
@Component
public class DataResetTask {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${menu.cache.key}")
    private String menuCacheKey;
    @Value("${dictionaries.cache.key}")
    private String dictionariesKey;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void resetData() {
        Connection connection = null;
        try {
            connection = getConnection();
            ClassPathResource classPathResource = new ClassPathResource("reset-data.sql");
            EncodedResource encodedResource = new EncodedResource(classPathResource, Constants.UTF_8);
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), encodedResource);
            redisCacheUtils.delete(menuCacheKey);
            redisCacheUtils.delete(dictionariesKey);
            logger.info("数据重置成功！");
        } catch (Exception e) {
            logger.error("数据重置异常", e);
        } finally {
            closeConnection(connection);
        }
    }

    private Connection getConnection() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }

    private void closeConnection(Connection connection) {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
