package com.qweather.leframework.base.rbac.role_permission.service;

import com.qweather.leframework.model.Query;
import com.qweather.leframework.base.rbac.role_permission.service.po.RolePermissionEntity;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface RolePermissionQuery extends Query<RolePermissionQuery, RolePermissionEntity> {

    RolePermissionQuery roleId(Long roleId);

    RolePermissionQuery permissionId(Long permissionId);

    RolePermissionQuery orderByRoleId(Query.Direction direction);

    RolePermissionQuery orderByPermissionId(Query.Direction direction);

}
