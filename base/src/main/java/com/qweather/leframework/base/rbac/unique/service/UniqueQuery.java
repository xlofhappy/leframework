package com.qweather.leframework.base.rbac.unique.service;

import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import com.qweather.leframework.model.Query;

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
