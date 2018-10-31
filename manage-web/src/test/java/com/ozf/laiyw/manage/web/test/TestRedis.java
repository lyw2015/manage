package com.ozf.laiyw.manage.web.test;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-servlet.xml", "classpath:spring-redis.xml"})
public class TestRedis {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Test
    public void get() {
        Map<String, String> map = redisCacheUtils.getCacheMap("shareSessionMapCache");
        System.out.println(map.size());
        for (String str : map.keySet()) {
            System.out.println("SessionID: " + str);
            System.out.println("Session: " + map.get(str));
        }
    }

    @Test
    public void hashKey() {
        Set<String> set = redisCacheUtils.redisTemplate.keys("*");
        System.out.println(set);
        Set list = redisCacheUtils.redisTemplate.boundHashOps("shareSessionMapCache").keys();
        System.out.println(list);
        //redisCacheUtils.redisTemplate.delete(set);
    }

    @Test
    public void test() {
        //redisCacheUtils.redisTemplate.boundHashOps("userMap").put("user5",new User());
        redisCacheUtils.redisTemplate.boundHashOps("userMap").delete("user1");
        System.out.println(JSON.toJSONString(redisCacheUtils.getCacheMap("userMap")));
    }

    @Test
    public void object() {
        String val = "value";

        //redisCacheUtils.setCacheObject("val",val);

        val = (String) redisCacheUtils.getCacheObject("val");
        System.out.println(val);

    }

    @Test
    public void list() {
        List<String> list = new ArrayList<>();
        list.add("9");
        list.add("8");
        list.add("7");

        //redisCacheUtils.setCacheList("lists",list);

        list = redisCacheUtils.getCacheList("lists");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void set() {
        Set<String> sets = new HashSet<>();
        sets.add("1");
        sets.add("2");
        sets.add("3");

        //redisCacheUtils.setCacheSet("sets",sets);

        sets = redisCacheUtils.getCacheSet("sets");
        System.out.println(JSON.toJSONString(sets));
    }

    @Test
    public void map() {
        Map<String, User> userMap = new HashMap<>();
        userMap.put("user1", new User());
        userMap.put("user2", new User());
        userMap.put("user3", new User());

        //redisCacheUtils.setCacheMap("userMap", userMap);
        userMap = redisCacheUtils.getCacheMap("userMap");
        System.out.println(JSON.toJSONString(userMap));
    }

    @Test
    public void mapExipre() {
        redisCacheUtils.redisTemplate.expire("user1", 2, TimeUnit.MINUTES);
    }
}
