package com.qweather.leframework.generator.worker.service.impl;

import com.qweather.leframework.generator.worker.service.cmd.ListWorkerNodeCmd;
import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeEntity;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;
import com.qweather.leframework.generator.worker.service.WorkerNodeQuery;
import com.qweather.leframework.generator.worker.service.cmd.CountWorkerNodeCmd;

import java.util.List;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class WorkerNodeQueryImpl extends AbstractQuery<WorkerNodeQuery, WorkerNodeEntity> implements WorkerNodeQuery {

    private Long id;
    private String hostName;
    private String port;
    private CommandExecutor commandExecutor;

    public WorkerNodeQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID("A.ID"), HOST("A.HOST_NAME"), PORT("A.PORT");
        private String name;

        Query(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public WorkerNodeQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public WorkerNodeQuery hostAndPort(String hostName, String port) {
        this.hostName = hostName;
        this.port = port;
        return this;
    }

    @Override
    public WorkerNodeQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountWorkerNodeCmd(this));
    }

    @Override
    protected List<WorkerNodeEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListWorkerNodeCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return "A.ID";
    }
}
