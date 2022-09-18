package org.le.base.dictionary.service;

import org.le.base.dictionary.service.po.DictionaryEntity;
import org.le.core.interfaces.DictionaryIndex;
import org.le.model.IdQuery;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public interface DictionaryQuery extends IdQuery<DictionaryQuery, DictionaryEntity> {

    DictionaryQuery pid(DictionaryIndex pid);

    DictionaryQuery code(String code);

    DictionaryQuery value(String value);

    DictionaryQuery orderByCode(Direction direction);

    DictionaryQuery orderByValue(Direction direction);

}
