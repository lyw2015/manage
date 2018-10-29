package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.dao.pagehelper.Page;
import com.ozf.laiyw.manage.model.Log;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/27 17:31
 */
public interface LogMapper {

    Page<Log> queryLog();

    int saveLog(Log log);
}
