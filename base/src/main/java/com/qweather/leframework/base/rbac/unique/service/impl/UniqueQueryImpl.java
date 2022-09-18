package com.qweather.leframework.base.rbac.unique.service.impl;

import com.qweather.leframework.base.rbac.unique.service.cmd.ListUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.UniqueQuery;
import com.qweather.leframework.base.rbac.unique.service.UniqueType;
import com.qweather.leframework.base.rbac.unique.service.cmd.CountUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class UniqueQueryImpl extends AbstractQuery<UniqueQuery, UniqueEntity> implements UniqueQuery {

    private Long uid;
    private String type;
    private String unionId;
    private CommandExecutor commandExecutor;

    public UniqueQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        UID(" A.UID "), UNION_ID(" A.UNION_ID ");
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
    public UniqueQuery uid(Long uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public UniqueQuery type(UniqueType type) {
        this.type = type.type();
        return this;
    }

    @Override
    public UniqueQuery unionId(String unionId) {
        this.unionId = unionId;
        return this;
    }

    @Override
    public UniqueQuery orderByUid(Direction direction) {
        return orderBy(Query.UID, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountUniqueCmd(this));
    }

    @Override
    protected List<UniqueEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListUniqueCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " A.UNION_ID ";
    }
}
