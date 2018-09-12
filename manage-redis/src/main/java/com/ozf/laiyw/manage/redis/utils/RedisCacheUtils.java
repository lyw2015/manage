package com.ozf.laiyw.manage.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheUtils<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 是否存在指定Key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 清除指定key的数据
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置key的有效时长（min）
     *
     * @param key
     * @param expiresTime
     */
    public void expire(String key, Integer expiresTime) {
        redisTemplate.expire(key, expiresTime, TimeUnit.MINUTES);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key
     * @param value
     * @return
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key
     * @param value
     * @param expiresTime 有效时长（min）
     * @return
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer expiresTime) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, expiresTime, TimeUnit.MINUTES);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key
     * @return
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存List数据
     *
     * @param key
     * @param dataList
     * @return
     */
    public <T> List<Long> setCacheList(String key, List<T> dataList) {
        List<Long> results = new ArrayList<Long>();
        if (null != dataList) {
            BoundListOperations boundListOperations = redisTemplate.boundListOps(key);
            int size = dataList.size();
            Long tempLong = null;
            for (int i = 0; i < size; i++) {
                tempLong = boundListOperations.rightPush(dataList.get(i));
                results.add(tempLong);
            }
        }
        return results;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key
     * @return
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        BoundListOperations boundListOperations = redisTemplate.boundListOps(key);
        for (int i = 0; i < boundListOperations.size(); i++) {
            dataList.add((T) boundListOperations.index(i));
        }
        return dataList;
    }

    /**
     * 刷新缓存的list对象
     *
     * @param key
     * @param dataList
     */
    public void refreshCacheList(String key, List<T> dataList) {
        redisTemplate.delete(key);
        setCacheList(key, dataList);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
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
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }
}
