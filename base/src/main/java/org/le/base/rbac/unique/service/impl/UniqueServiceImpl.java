package org.le.base.rbac.unique.service.impl;

import org.le.base.rbac.unique.service.UniqueQuery;
import org.le.base.rbac.unique.service.UniqueService;
import org.le.base.rbac.unique.service.UniqueType;
import org.le.base.rbac.unique.service.cmd.DeleteUniqueByUidAndTypeCmd;
import org.le.base.rbac.unique.service.cmd.DeleteUniqueByUidCmd;
import org.le.base.rbac.unique.service.cmd.DeleteUniqueCmd;
import org.le.base.rbac.unique.service.cmd.InsertUniqueCmd;
import org.le.base.rbac.unique.service.po.UniqueEntity;
import org.le.model.command.CommandExecutor;
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
