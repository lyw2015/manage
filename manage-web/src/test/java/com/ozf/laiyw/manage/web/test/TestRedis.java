package com.ozf.laiyw.manage.web.test;

import com.ozf.laiyw.manage.common.utils.SerializeUtil;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.apache.shiro.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-servlet.xml", "classpath:spring-redis.xml"})
public class TestRedis {

    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${session.shareSessionMapCache}")
    private String shareSessionMapCache;


    @Test
    public void gethareSessSionMapCache() {
        Map<String, String> map = redisCacheUtils.getCacheMap(shareSessionMapCache);
        for (String str : map.keySet()) {
            System.out.println("SessionID--->" + str);
            System.out.println("Timeout--->" + ((Session) SerializeUtil.deserialize(String.valueOf(map.get(str)))).getTimeout());
            System.out.println("Session--->" + map.get(str));
            System.out.println();
        }
    }
}
