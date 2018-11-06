package com.ozf.laiyw.manage.service;

import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 17:02
 */
public interface SystemService {

    //修改系统配置
    int updateSystemConfig(String type, String jsondata);

    String getJsondataByType(String type);

    Map<String, String> getConfigs();
}
