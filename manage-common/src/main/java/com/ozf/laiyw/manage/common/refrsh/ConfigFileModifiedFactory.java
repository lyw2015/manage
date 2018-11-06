package com.ozf.laiyw.manage.common.refrsh;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/6 12:23
 */
public class ConfigFileModifiedFactory {

    private static ConfigFileModifiedFactory configFactory = new ConfigFileModifiedFactory();
    private Map<String, Long> fileModifiedMap = new HashMap<String, Long>();

    public static ConfigFileModifiedFactory getInstance() {
        return configFactory;
    }

    public void put(String key, long lastModified) {
        fileModifiedMap.put(key, lastModified);
    }

    public Long get(String key) {
        return fileModifiedMap.get(key);
    }
}
