package com.qweather.leframework.base.variable.service.impl;

import com.qweather.leframework.base.variable.service.VariableQuery;
import com.qweather.leframework.base.variable.service.cmd.CountVariableCmd;
import com.qweather.leframework.base.variable.service.cmd.ListVariableCmd;
import com.qweather.leframework.base.variable.service.po.VariableEntity;
import com.qweather.leframework.core.interfaces.VariableCode;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class VariableQueryImpl extends AbstractQuery<VariableQuery, VariableEntity> implements VariableQuery {

    private Long id;
    private String code;
    private String value;
    private final CommandExecutor commandExecutor;

    public VariableQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID("A.ID"), CODE("A.ITEM_CODE"), VALUE("A.ITEM_VALUE");
        private final String name;

        Query(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public VariableQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public VariableQuery code(VariableCode variableCode) {
        this.code = variableCode.code();
        return this;
    }

    @Override
    public VariableQuery value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public VariableQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    public VariableQuery orderByCode(Direction direction) {
        return orderBy(Query.CODE, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountVariableCmd(this));
    }

    @Override
    protected List<VariableEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListVariableCmd(this));
    }
}
