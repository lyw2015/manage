package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.dao.mapper.SystemMapper;
import com.ozf.laiyw.manage.service.SystemService;
import com.ozf.laiyw.manage.common.commons.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 17:03
 */
@Transactional
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @PostConstruct
    public void cacheData() {
        Map<String, String> result = new HashMap<>();
        List<Map<String, String>> listMap = systemMapper.getConfigs();
        for (Map map : listMap) {
            result.put(map.get("sys_type").toString(), map.get("sys_jsondata").toString());
        }
        SystemConfig.set(result);
    }

    @Override
    public int updateSystemConfig(String type, String jsondata) {
        int count = systemMapper.updateSystemConfig(type, jsondata);
        if (count == 1) {
            cacheData();
        }
        return count;
    }

    @Override
    public String getJsondataByType(String type) {
        return SystemConfig.getDataByType(type);
    }

    @Override
    public Map<String, String> getConfigs() {
        return SystemConfig.getDataMap();
    }
}
