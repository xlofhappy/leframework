package com.qweather.leframework.base.rbac.role.service;

import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface RoleService {

    RoleQuery createRoleQuery();

    RoleEntity createRole();

    void saveRole(RoleEntity entity);

    void deleteRole(Long id);

    void deleteRoleForever(Long id);

    void grantPermission(UserEntity userEntity, Long roleId, Long[] permissionIds);

}
