package org.le.base.rbac.role_permission.service.impl;

import org.le.base.rbac.role_permission.service.RolePermissionQuery;
import org.le.base.rbac.role_permission.service.RolePermissionService;
import org.le.base.rbac.role_permission.service.cmd.DeleteRolePermissionCmd;
import org.le.base.rbac.role_permission.service.cmd.InsertRolePermissionCmd;
import org.le.base.rbac.role_permission.service.po.RolePermissionEntity;
import org.le.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final CommandExecutor executor;

    @Autowired
    public RolePermissionServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public RolePermissionQuery createRolePermissionQuery() {
        return new RolePermissionQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRolePermission(RolePermissionEntity entity) {
        executor.execute(new InsertRolePermissionCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRolePermission(Long roleId, Long permissionId) {
        executor.execute(new DeleteRolePermissionCmd(roleId, permissionId));
    }



}
