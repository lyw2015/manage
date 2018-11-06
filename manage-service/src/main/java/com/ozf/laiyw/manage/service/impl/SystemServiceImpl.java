package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.dao.mapper.SystemMapper;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.SystemService;
import com.ozf.laiyw.manage.service.utils.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    private final Logger logger = Logger.getLogger(this.getClass());
    @Value("${system.config.cache.key}")
    private String cacheKey;
    @Autowired
    private SystemMapper systemMapper;
    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @PostConstruct
    public void cacheData() {
        Map<String, String> result = redisCacheUtils.getCacheMap(cacheKey);
        List<Map<String, String>> listMap = systemMapper.getConfigs();
        for (Map map : listMap) {
            result.put(map.get("sys_type").toString(), map.get("sys_jsondata").toString());
        }
        redisCacheUtils.setCacheMap(cacheKey, result);
        SystemConfig.set(result);
        logger.info("cache system config data--->" + result.size());
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
        return (String) redisCacheUtils.getMapDataByKey(cacheKey, type);
    }

    @Override
    public Map<String, String> getConfigs() {
        return redisCacheUtils.getCacheMap(cacheKey);
    }
}
