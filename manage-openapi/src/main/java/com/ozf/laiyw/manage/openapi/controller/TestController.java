package com.ozf.laiyw.manage.openapi.controller;

import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.inf.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/openapi")
@RestController
public class TestController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private TestService testService;
    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @RequestMapping("/test")
    public String test() {
        boolean bl = redisCacheUtils.hasKey("test");
        logger.info("openapi...redis has key: " + bl);
        return testService.testFind();
    }
}
