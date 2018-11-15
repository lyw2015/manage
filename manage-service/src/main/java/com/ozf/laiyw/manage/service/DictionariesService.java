package com.ozf.laiyw.manage.service;

import com.ozf.laiyw.manage.model.Dictionaries;
import com.ozf.laiyw.manage.model.DictionariesItem;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/15 14:38
 */
public interface DictionariesService {

    int updateDictionariesStatus(String id);

    int updateDictionariesItemStatus(String id);

    List<Dictionaries> getAllDictionaries();

    List<DictionariesItem> getDictionariesItemByDid(String dictId);

    List<DictionariesItem> getDictionariesItemByIdentification(String identification);

    int saveDictionaries(Dictionaries dictionaries);

    int updateDictionaries(Dictionaries dictionaries);

    int saveDictionariesItem(DictionariesItem dictionariesItem);

    int updateDictionariesItem(DictionariesItem dictionariesItem);

    Dictionaries getDictionariesById(String id);

    DictionariesItem getDictionariesItemById(String id);
}
