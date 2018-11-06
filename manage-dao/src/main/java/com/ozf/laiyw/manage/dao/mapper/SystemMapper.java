package com.ozf.laiyw.manage.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 17:08
 */
public interface SystemMapper {

    int updateSystemConfig(@Param("type") String type, @Param("jsondata") String jsondata);

    String getJsondataByType(String type);

    List<Map<String, String>> getConfigs();
}
