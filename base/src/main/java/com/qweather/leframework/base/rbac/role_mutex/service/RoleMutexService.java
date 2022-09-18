package com.qweather.leframework.base.rbac.role_mutex.service;

import com.qweather.leframework.base.rbac.role_mutex.service.po.RoleMutexEntity;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public interface RoleMutexService {

    RoleMutexQuery createRoleMutexQuery();

    void addRoleMutex(RoleMutexEntity entity);

    void deleteRoleMutex(Long roleA, Long roleB);

}
