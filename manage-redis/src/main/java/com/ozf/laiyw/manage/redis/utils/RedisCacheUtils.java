package com.ozf.laiyw.manage.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheUtils<T> {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 删除指定key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key
     * @param value
     */
    public void setCacheObject(String key, T value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key
     * @param value
     * @param expiresTime 有效时长（min）
     * @param timeUnit
     */
    public void setCacheObject(String key, T value, Integer expiresTime, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).set(value, expiresTime, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key
     * @return
     */
    public <T> T getCacheObject(String key) {
        BoundValueOperations<String, T> boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.get();
    }

    /**
     * 缓存List数据
     *
     * @param key
     * @param dataList
     * @return
     */
    public <T> void setCacheList(String key, List<T> dataList) {
        if (null != dataList && !dataList.isEmpty()) {
            BoundListOperations<String, T> boundListOperations = redisTemplate.boundListOps(key);
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                boundListOperations.rightPush(dataList.get(i));
            }
        }
    }

    /**
     * 获得缓存的list对象
     *
     * @param key
     * @return
     */
    public <T> List<T> getCacheList(String key) {
        BoundListOperations<String, T> boundListOperations = redisTemplate.boundListOps(key);
        List<T> dataList = new ArrayList<T>();
        for (int i = 0; i < boundListOperations.size(); i++) {
            dataList.add(boundListOperations.index(i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> void setCacheSet(String key, Set<T> dataSet) {
        if (null != dataSet && !dataSet.isEmpty()) {
            BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
            Iterator<T> it = dataSet.iterator();
            while (it.hasNext()) {
                setOperation.add(it.next());
            }
        }
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        Cursor<T> cursor = redisTemplate.boundSetOps(key).scan(ScanOptions.NONE);
        while (cursor.hasNext()) {
            dataSet.add(cursor.next());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> void setCacheMap(String key, Map<String, T> dataMap) {
        if (null != dataMap && !dataMap.isEmpty()) {
            BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
            boundHashOperations.putAll(dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * 在指定的map中添加键值对
     *
     * @param mapKey
     * @param dataKey
     * @param value
     */
    public void addMapData(String mapKey, String dataKey, T value) {
        redisTemplate.boundHashOps(mapKey).put(dataKey, value);
    }

    /**
     * 获取指定map中的键值对
     *
     * @param mapKey
     * @param dataKey
     * @param <T>
     * @return
     */
    public <T> T getMapDataByKey(String mapKey, String dataKey) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(mapKey);
        return boundHashOperations.get(dataKey);
    }

    /**
     * 删除指定map中的键值对
     *
     * @param mapKey
     * @param dataKey
     * @return
     */
    public long deleteMapDataByKey(String mapKey, String dataKey) {
        return redisTemplate.boundHashOps(mapKey).delete(dataKey);
    }
}
