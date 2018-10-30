package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.dao.mapper.LogMapper;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageInfo queryLog(PageInfo pageInfo, Log log) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(logMapper.queryLog(log));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public Log detailLog(String id) {
        return logMapper.detailLog(id);
    }

    @Override
    public int saveLog(Log log) {
        return logMapper.saveLog(log);
    }
}
