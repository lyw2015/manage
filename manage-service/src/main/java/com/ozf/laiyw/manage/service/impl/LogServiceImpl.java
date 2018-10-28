package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.dao.mapper.LogMapper;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/27 17:26
 */
@Transactional
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public List<Log> queryLog() {
        return logMapper.queryLog();
    }
}
