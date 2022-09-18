package com.qweather.leframework.base.rbac.user_role.service;

import com.qweather.leframework.base.rbac.user_role.service.po.UserRoleEntity;
import com.qweather.leframework.model.Query;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public interface UserRoleQuery extends Query<UserRoleQuery, UserRoleEntity> {

    UserRoleQuery userId(Long userId);

    UserRoleQuery roleId(Long roleId);

    UserRoleQuery orderByUid(Direction direction);

    UserRoleQuery orderByRoleId(Direction direction);

}
