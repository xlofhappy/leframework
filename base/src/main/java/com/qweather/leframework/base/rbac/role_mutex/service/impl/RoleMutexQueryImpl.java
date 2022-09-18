package com.qweather.leframework.base.rbac.role_mutex.service.impl;

import com.qweather.leframework.base.rbac.role_mutex.service.RoleMutexQuery;
import com.qweather.leframework.base.rbac.role_mutex.service.cmd.CountRoleMutexCmd;
import com.qweather.leframework.base.rbac.role_mutex.service.cmd.ListRoleMutexCmd;
import com.qweather.leframework.base.rbac.role_mutex.service.po.RoleMutexEntity;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.command.CommandExecutor;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.OrderBy;

import java.util.List;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public class RoleMutexQueryImpl extends AbstractQuery<RoleMutexQuery, RoleMutexEntity> implements RoleMutexQuery {


    private Long roleA;
    private Long roleB;
    private CommandExecutor  commandExecutor;

    public RoleMutexQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ROLE_A("A.ROLE_A"), ROLE_B("A.ROLE_B");
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
    public RoleMutexQuery roleA(Long roleA) {
        this.roleA = roleA;
        return this;
    }

    @Override
    public RoleMutexQuery roleB(Long roleB) {
        this.roleB = roleB;
        return this;
    }

    @Override
    public RoleMutexQuery orderByRoleA(Direction direction) {
        return orderBy(Query.ROLE_A, direction);
    }

    @Override
    public RoleMutexQuery orderByRoleB(Direction direction) {
        return orderBy(Query.ROLE_B, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountRoleMutexCmd(this));
    }

    @Override
    protected List<RoleMutexEntity> executeList(Page page) {
        if ( page != null ) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListRoleMutexCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " " + Query.ROLE_A.getName() + " ";
    }
}
