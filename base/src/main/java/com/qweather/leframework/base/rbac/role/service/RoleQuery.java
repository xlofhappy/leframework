package com.qweather.leframework.base.rbac.role.service;

import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import com.qweather.leframework.model.IdQuery;
import com.qweather.leframework.model.Query;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface RoleQuery extends IdQuery<RoleQuery, RoleEntity> {

    RoleQuery pid(Long pid);

    RoleQuery code(String code);

    RoleQuery name(String name);

    RoleQuery permissionLimit(Integer permissionLimit);

    RoleQuery needRoleId(Long needRoleId);

    RoleQuery orderByPid(Query.Direction direction);

    RoleQuery orderByCode(Query.Direction direction);

    RoleQuery orderByName(Query.Direction direction);

    RoleQuery orderByPermissionLimit(Query.Direction direction);



}
