package com.qweather.leframework.base.rbac.unique.service.impl;

import com.qweather.leframework.base.rbac.unique.service.UniqueQuery;
import com.qweather.leframework.base.rbac.unique.service.UniqueService;
import com.qweather.leframework.base.rbac.unique.service.UniqueType;
import com.qweather.leframework.base.rbac.unique.service.cmd.DeleteUniqueByUidAndTypeCmd;
import com.qweather.leframework.base.rbac.unique.service.cmd.DeleteUniqueByUidCmd;
import com.qweather.leframework.base.rbac.unique.service.cmd.DeleteUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.cmd.InsertUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
@Service
public class UniqueServiceImpl implements UniqueService {

    private final CommandExecutor executor;

    @Autowired
    public UniqueServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public UniqueQuery createUniqueQuery() {
        return new UniqueQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUnique(UniqueEntity entity) {
        executor.execute(new InsertUniqueCmd(entity));
    }

    @Override
    public void deleteUnique(String unionId, UniqueType uniqueType) {
        executor.execute(new DeleteUniqueCmd(unionId, uniqueType));
    }

    @Override
    public void deleteUniqueByUid(Long uid) {
        executor.execute(new DeleteUniqueByUidCmd(uid));
    }

    @Override
    public void deleteUniqueByUidAndType(Long uid, UniqueType uniqueType) {
        executor.execute(new DeleteUniqueByUidAndTypeCmd(uid, uniqueType));
    }
}
