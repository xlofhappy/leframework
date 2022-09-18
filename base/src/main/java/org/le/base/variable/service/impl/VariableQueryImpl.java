package org.le.base.variable.service.impl;

import org.le.base.variable.service.VariableQuery;
import org.le.base.variable.service.cmd.CountVariableCmd;
import org.le.base.variable.service.cmd.ListVariableCmd;
import org.le.base.variable.service.po.VariableEntity;
import org.le.core.interfaces.VariableCode;
import org.le.core.result.Page;
import org.le.model.AbstractQuery;
import org.le.model.OrderBy;
import org.le.model.command.CommandExecutor;

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
