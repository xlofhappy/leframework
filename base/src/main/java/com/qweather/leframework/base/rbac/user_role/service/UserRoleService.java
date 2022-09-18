package com.qweather.leframework.base.rbac.user_role.service;

import com.qweather.leframework.base.rbac.user_role.service.po.UserRoleEntity;

/**
 * Created at 2018-08-03 14:44:56
 * @author xiaole
 */
public interface UserRoleService {

    UserRoleQuery createUserRoleQuery();

    void addUserRole(UserRoleEntity entity);

    void deleteUserRole(Long userId, Long roleId);

}
