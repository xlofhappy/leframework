package org.le.base.rbac.user_role.service.impl;

import org.le.base.rbac.user_role.service.UserRoleQuery;
import org.le.base.rbac.user_role.service.UserRoleService;
import org.le.base.rbac.user_role.service.cmd.DeleteUserRoleCmd;
import org.le.base.rbac.user_role.service.cmd.InsertUserRoleCmd;
import org.le.base.rbac.user_role.service.po.UserRoleEntity;
import org.le.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final CommandExecutor executor;

    @Autowired
    public UserRoleServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public UserRoleQuery createUserRoleQuery() {
        return new UserRoleQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRole(UserRoleEntity entity) {
        executor.execute(new InsertUserRoleCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(Long userId, Long roleId) {
        executor.execute(new DeleteUserRoleCmd(userId, roleId));
    }

}
