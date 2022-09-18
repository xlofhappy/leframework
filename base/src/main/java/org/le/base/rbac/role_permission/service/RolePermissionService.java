package org.le.base.rbac.role_permission.service;

import org.le.base.rbac.role_permission.service.po.RolePermissionEntity;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface RolePermissionService {

    RolePermissionQuery createRolePermissionQuery();

    void addRolePermission(RolePermissionEntity entity);

    void deleteRolePermission(Long roleId, Long permissionId);

}
