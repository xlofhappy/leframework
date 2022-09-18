package com.qweather.leframework.generator.worker.service.impl;

import com.qweather.leframework.generator.worker.service.cmd.InsertWorkerNodeCmd;
import com.qweather.leframework.generator.worker.service.cmd.UpdateWorkerNodeTimeCmd;
import com.qweather.leframework.model.command.CommandExecutor;
import com.qweather.leframework.generator.worker.service.WorkerNodeQuery;
import com.qweather.leframework.generator.worker.service.WorkerNodeService;
import com.qweather.leframework.generator.worker.service.cmd.DeleteWorkerNodeCmd;
import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2019-08-07 22:15:41
 *
 * @author xiaole
 */
@Service
public class WorkerNodeServiceImpl implements WorkerNodeService {

    private final CommandExecutor executor;

    @Autowired
    public WorkerNodeServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public WorkerNodeQuery createWorkerNodeQuery() {
        return new WorkerNodeQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWorkerNode(WorkerNodeEntity entity) {
        executor.execute(new InsertWorkerNodeCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWorkerNode(Long id) {
        executor.execute(new DeleteWorkerNodeCmd(id));
    }

    @Override
    public void keepAliveWorkerNode(Long id, String time) {
        executor.execute(new UpdateWorkerNodeTimeCmd(id, time));
    }
}
