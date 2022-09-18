package com.qweather.leframework.base.rbac.user_extend.service.impl;

import com.qweather.leframework.base.rbac.user_extend.service.cmd.DeleteUserExtendCmd;
import com.qweather.leframework.base.rbac.user_extend.service.cmd.UpdateValueByUserIdAndKCmd;
import com.qweather.leframework.base.rbac.user_extend.service.UserExtendQuery;
import com.qweather.leframework.base.rbac.user_extend.service.UserExtendService;
import com.qweather.leframework.base.rbac.user_extend.service.cmd.InsertUserExtendCmd;
import com.qweather.leframework.base.rbac.user_extend.service.po.UserExtendEntity;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
@Service
public class UserExtendServiceImpl implements UserExtendService {

    private final CommandExecutor executor;

    @Autowired
    public UserExtendServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public UserExtendQuery createUserExtendQuery() {
        return new UserExtendQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserExtend(UserExtendEntity entity) {
        executor.execute(new InsertUserExtendCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserExtend(Long uid, Integer k) {
        executor.execute(new DeleteUserExtendCmd(uid, k));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserExtend(Long uid, Integer k, String v) {
        executor.execute(new UpdateValueByUserIdAndKCmd(uid, k, v));
    }

    @Override
    public void updateUserExtend(UserExtendEntity userExtendEntity) {
        executor.execute(new UpdateValueByUserIdAndKCmd(userExtendEntity));
    }
}
