package com.qweather.leframework.base.rbac.role_mutex.service;

import com.qweather.leframework.base.rbac.role_mutex.service.po.RoleMutexEntity;
import com.qweather.leframework.model.Query;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public interface RoleMutexQuery extends Query<RoleMutexQuery, RoleMutexEntity> {

    RoleMutexQuery roleA(Long roleA);

    RoleMutexQuery roleB(Long roleB);

    RoleMutexQuery orderByRoleA(Query.Direction direction);

    RoleMutexQuery orderByRoleB(Query.Direction direction);

}
