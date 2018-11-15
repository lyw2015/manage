package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.Dictionaries;
import com.ozf.laiyw.manage.model.DictionariesItem;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/14 18:04
 */
public interface DictionariesMapper {

    int updateDictionariesStatus(String id);

    int updateDictionariesItemStatus(String id);

    List<Dictionaries> getAllDictionaries();

    List<Dictionaries> getAllDictItem();

    List<DictionariesItem> getDictionariesItemByDid(String dictId);

    List<DictionariesItem> getDictionariesItemByIdentification(String identification);

    int saveDictionaries(Dictionaries dictionaries);

    int updateDictionaries(Dictionaries dictionaries);

    int saveDictionariesItem(DictionariesItem dictionariesItem);

    int updateDictionariesItem(DictionariesItem dictionariesItem);

    Dictionaries getDictionariesById(String id);

    DictionariesItem getDictionariesItemById(String id);

}
