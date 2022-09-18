package com.qweather.leframework.base.rbac.permission.service;

import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface PermissionService {

    PermissionQuery createPermissionQuery();

    PermissionEntity createPermission();

    void addPermission(PermissionEntity entity);

    void savePermission(PermissionEntity entity);

    void deletePermission(Long id);

    @Nonnull List<PermissionEntity> listMenuByUser(UserEntity userEntity, Long menuRootId);
}
