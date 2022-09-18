package com.qweather.leframework.base.variable.service.impl;

import com.qweather.leframework.base.variable.service.cmd.InsertVariableCmd;
import com.qweather.leframework.base.common.util.VoUtil;
import com.qweather.leframework.base.variable.service.VariableQuery;
import com.qweather.leframework.base.variable.service.VariableService;
import com.qweather.leframework.base.variable.service.cmd.DeleteVariableCmd;
import com.qweather.leframework.base.variable.service.cmd.DeleteVariableForeverCmd;
import com.qweather.leframework.base.variable.service.cmd.UpdateVariableCmd;
import com.qweather.leframework.base.variable.service.po.VariableEntity;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
@Service
public class VariableServiceImpl implements VariableService {

    private final CommandExecutor executor;

    @Autowired
    public VariableServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public VariableQuery createVariableQuery() {
        return new VariableQueryImpl(executor);
    }

    @Override
    public VariableEntity createVariable() {
        return new VariableEntity(Config.getDaoConfig().getIdPool().getNextId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveVariable(VariableEntity entity) {
        if (entity != null && entity.getId() != null) {
            VariableEntity one = this.createVariableQuery().id(entity.getId()).one();
            VoUtil.setLePoCommonProperty(entity);
            if (one == null) {
                executor.execute(new InsertVariableCmd(entity));
            } else {
                one.setValue(entity.getValue());
                executor.execute(new UpdateVariableCmd(entity));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVariable(Long id) {
        executor.execute(new DeleteVariableCmd(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVariableForever(Long id) {
        executor.execute(new DeleteVariableForeverCmd(id));
    }

}
