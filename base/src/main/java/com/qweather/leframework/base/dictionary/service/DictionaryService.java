package com.qweather.leframework.base.dictionary.service;

import com.qweather.leframework.base.dictionary.service.po.DictionaryEntity;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public interface DictionaryService {

    DictionaryQuery createDictionaryQuery();

    DictionaryEntity createDictionary();

    void saveDictionary(DictionaryEntity entity);

    void deleteDictionary(Long id);

    void deleteDictionaryForever(Long id);

}
