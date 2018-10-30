package com.ozf.laiyw.manage.service;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.Log;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/27 17:25
 */
public interface LogService {

    PageInfo queryLog(PageInfo pageInfo, Log log);

    Log detailLog(String id);

    int saveLog(Log log);
}
