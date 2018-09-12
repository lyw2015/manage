package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.dao.mapper.TestMapper;
import com.ozf.laiyw.manage.service.inf.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private TestMapper testMapper;

    @Override
    public String testFind() {
        logger.info("--------find star----------");
        return testMapper.testFind();
    }
}
