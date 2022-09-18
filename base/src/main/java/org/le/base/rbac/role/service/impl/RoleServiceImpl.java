package org.le.base.rbac.role.service.impl;

import org.le.base.common.util.VoUtil;
import org.le.base.rbac.permission.service.cmd.GetPermissionCmd;
import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.le.base.rbac.role.service.RoleQuery;
import org.le.base.rbac.role.service.RoleService;
import org.le.base.rbac.role.service.cmd.*;
import org.le.base.rbac.role.service.po.RoleEntity;
import org.le.base.rbac.role_mutex.service.cmd.DeleteRoleMutexCmd;
import org.le.base.rbac.role_mutex.service.impl.RoleMutexQueryImpl;
import org.le.base.rbac.role_mutex.service.po.RoleMutexEntity;
import org.le.base.rbac.role_permission.service.cmd.DeleteRolePermissionByRoleIdCmd;
import org.le.base.rbac.role_permission.service.cmd.DeleteRolePermissionCmd;
import org.le.base.rbac.role_permission.service.cmd.MultiInsertRolePermissionCmd;
import org.le.base.rbac.role_permission.service.po.RolePermissionEntity;
import org.le.base.rbac.user.service.po.UserEntity;
import org.le.base.rbac.user_role.service.cmd.DeleteUserRoleCmd;
import org.le.base.rbac.user_role.service.impl.UserRoleQueryImpl;
import org.le.base.rbac.user_role.service.po.UserRoleEntity;
import org.le.core.config.Config;
import org.le.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final CommandExecutor executor;

    @Autowired
    public RoleServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public RoleQuery createRoleQuery() {
        return new RoleQueryImpl(this.executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleEntity createRole() {
        return new RoleEntity(Config.getDaoConfig().getIdPool().getNextId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(RoleEntity entity) {
        if (entity != null) {
            boolean newEntity = false;
            if (entity.getId() != null) {
                RoleEntity one = createRoleQuery().id(entity.getId()).one();
                if (one != null) {
                    one.setCode(entity.getCode());
                    one.setDescription(entity.getDescription());
                    one.setName(entity.getName());
                    one.setNeedRoleId(entity.getNeedRoleId());
                    one.setPermissionLimit(entity.getPermissionLimit());
                    VoUtil.setLePoCommonProperty(one);
                    executor.execute(new UpdateRoleCmd(one));
                } else {
                    newEntity = true;
                }
            } else {
                newEntity = true;
            }
            if (newEntity) {
                VoUtil.setLePoCommonProperty(entity);
                executor.execute(new InsertRoleCmd(entity));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        RoleEntity one = this.createRoleQuery().id(id).one();
        if (one != null) {

            if (one.getChildren() != null) {
                for (RoleEntity child : one.getChildren()) {
                    deleteRole(child.getId());
                }
            }

            //delete role mutex link
            List<RoleMutexEntity> list = new RoleMutexQueryImpl(executor).roleA(id).list();
            if (list != null) {
                for (RoleMutexEntity roleMutexEntity : list) {
                    executor.execute(new DeleteRoleMutexCmd(roleMutexEntity.getRoleA(), roleMutexEntity.getRoleB()));
                }
            }
            //delete user role link
            List<UserRoleEntity> userRoleEntities = new UserRoleQueryImpl(executor).roleId(id).list();
            if (userRoleEntities != null) {
                for (UserRoleEntity userRoleEntity : userRoleEntities) {
                    executor.execute(new DeleteUserRoleCmd(userRoleEntity.getUserId(), userRoleEntity.getRoleId()));
                }
            }
            //delete role permission link
            executor.execute(new DeleteRolePermissionByRoleIdCmd(id));
            //delete role
            executor.execute(new DeleteRoleCmd(id));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleForever(Long id) {
        executor.execute(new DeleteRoleForeverCmd(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantPermission(UserEntity userEntity, Long roleId, Long[] permissionIds) {
        if (permissionIds != null && permissionIds.length > 0 && roleId != null) {
            RoleEntity roleEntity = executor.execute(new GetRoleByIdCmd(roleId));
            if (roleEntity != null) {
                List<PermissionEntity> allPermissionEntity = new ArrayList<>();
                List<PermissionEntity> waitForAdd = new ArrayList<>();
                for (int i = 0; i < permissionIds.length; i++) {
                    PermissionEntity permissionEntity = executor.execute(new GetPermissionCmd(permissionIds[i]));
                    if (permissionEntity != null) {
                        if (userEntity.getPermissionEntities().contains(permissionEntity) && !roleEntity.getPermissionEntities().contains(permissionEntity)) {
                            waitForAdd.add(permissionEntity);
                        }
                        allPermissionEntity.add(permissionEntity);
                    }
                }

                List<PermissionEntity> waitForDelete = roleEntity.getPermissionEntities().stream().filter(permissionEntity -> !allPermissionEntity.contains(permissionEntity)).collect(Collectors.toList());

                List<RolePermissionEntity> add = new ArrayList<>();
                for (PermissionEntity permissionEntity : waitForAdd) {
                    RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
                    rolePermissionEntity.setRoleId(roleEntity.getId());
                    rolePermissionEntity.setPermissionId(permissionEntity.getId());
                    add.add(rolePermissionEntity);
                }

                executor.execute(new MultiInsertRolePermissionCmd(add));

                for (PermissionEntity permissionEntity : waitForDelete) {
                    executor.execute(new DeleteRolePermissionCmd(roleEntity.getId(), permissionEntity.getId()));
                }
            }
        }
    }
}
