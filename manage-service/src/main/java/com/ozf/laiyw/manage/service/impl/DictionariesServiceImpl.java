package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.DictionariesMapper;
import com.ozf.laiyw.manage.model.Dictionaries;
import com.ozf.laiyw.manage.model.DictionariesItem;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.DictionariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/15 14:39
 */
@Transactional
@Service
public class DictionariesServiceImpl implements DictionariesService {

    @Autowired
    private DictionariesMapper dictionariesMapper;
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${dictionaries.cache.key}")
    private String dictionariesKey;

    @PostConstruct
    public void init() {
        Map<String, List<DictionariesItem>> listMap = new HashMap<>();
        List<Dictionaries> list = dictionariesMapper.getAllDictItem();
        for (Dictionaries dictionaries : list) {
            listMap.put(dictionaries.getIdentification(), dictionaries.getDictionariesItemList());
        }
        redisCacheUtils.setCacheMap(dictionariesKey, listMap);
    }

    @Override
    public int updateDictionariesStatus(String id) {
        List<DictionariesItem> list = getDictionariesItemByDid(id);
        if (null != list && list.size() > 0)
            return -1;
        init();
        return dictionariesMapper.updateDictionariesStatus(id);
    }

    @Override
    public int updateDictionariesItemStatus(String id) {
        int count = dictionariesMapper.updateDictionariesItemStatus(id);
        init();
        return count;
    }

    @Override
    public List<Dictionaries> getAllDictionaries() {
        return dictionariesMapper.getAllDictionaries();
    }

    @Override
    public List<DictionariesItem> getDictionariesItemByDid(String dictId) {
        return dictionariesMapper.getDictionariesItemByDid(dictId);
    }

    @Override
    public List<DictionariesItem> getDictionariesItemByIdentification(String identification) {
        List<DictionariesItem> list = (List<DictionariesItem>) redisCacheUtils.getMapDataByKey(dictionariesKey, identification);
        if (null == list || list.isEmpty())
            init();
        list = (List<DictionariesItem>) redisCacheUtils.getMapDataByKey(dictionariesKey, identification);
        return list;
    }

    @Override
    public int saveDictionaries(Dictionaries dictionaries) {
        dictionaries.setId(StringUtils.randUUID());
        dictionaries.setUpdateTime(DateUtils.getDateTime());
        int count = dictionariesMapper.saveDictionaries(dictionaries);
        init();
        return count;
    }

    @Override
    public int updateDictionaries(Dictionaries dictionaries) {
        dictionaries.setUpdateTime(DateUtils.getDateTime());
        int count = dictionariesMapper.updateDictionaries(dictionaries);
        init();
        return count;
    }

    @Override
    public int saveDictionariesItem(DictionariesItem dictionariesItem) {
        dictionariesItem.setId(StringUtils.randUUID());
        int count = dictionariesMapper.saveDictionariesItem(dictionariesItem);
        init();
        return count;
    }

    @Override
    public int updateDictionariesItem(DictionariesItem dictionariesItem) {
        int count = dictionariesMapper.updateDictionariesItem(dictionariesItem);
        init();
        return count;
    }

    @Override
    public Dictionaries getDictionariesById(String id) {
        return dictionariesMapper.getDictionariesById(id);
    }

    @Override
    public DictionariesItem getDictionariesItemById(String id) {
        return dictionariesMapper.getDictionariesItemById(id);
    }
}
