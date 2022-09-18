package org.le.base.rbac.unique.service;

import org.le.base.rbac.unique.service.po.UniqueEntity;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public interface UniqueService {

    UniqueQuery createUniqueQuery();

    void addUnique(UniqueEntity entity);

    void deleteUnique(String unionId, UniqueType uniqueType);

    void deleteUniqueByUid(Long uid);

    void deleteUniqueByUidAndType(Long uid, UniqueType uniqueType);
}
