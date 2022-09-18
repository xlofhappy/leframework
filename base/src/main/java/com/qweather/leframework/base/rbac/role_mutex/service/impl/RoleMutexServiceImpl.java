package com.qweather.leframework.base.rbac.role_mutex.service.impl;

import com.qweather.leframework.base.rbac.role_mutex.service.RoleMutexQuery;
import com.qweather.leframework.base.rbac.role_mutex.service.RoleMutexService;
import com.qweather.leframework.base.rbac.role_mutex.service.cmd.DeleteRoleMutexCmd;
import com.qweather.leframework.base.rbac.role_mutex.service.cmd.InsertRoleMutexCmd;
import com.qweather.leframework.base.rbac.role_mutex.service.po.RoleMutexEntity;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
@Service
public class RoleMutexServiceImpl implements RoleMutexService {

    private final CommandExecutor executor;

    @Autowired
    public RoleMutexServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public RoleMutexQuery createRoleMutexQuery() {
        return new RoleMutexQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleMutex(RoleMutexEntity entity) {
        executor.execute(new InsertRoleMutexCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMutex(Long roleA, Long roleB) {
        executor.execute(new DeleteRoleMutexCmd(roleA, roleB));
    }

}
