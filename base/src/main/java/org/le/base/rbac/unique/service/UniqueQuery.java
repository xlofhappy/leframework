package org.le.base.rbac.unique.service;

import org.le.base.rbac.unique.service.po.UniqueEntity;
import org.le.model.Query;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public interface UniqueQuery extends Query<UniqueQuery, UniqueEntity> {

    UniqueQuery uid(Long uid);

    UniqueQuery type(UniqueType type);

    UniqueQuery unionId(String unionId);

    UniqueQuery orderByUid(Direction direction);

}
