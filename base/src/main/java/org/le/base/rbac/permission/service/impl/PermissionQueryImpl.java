package org.le.base.rbac.permission.service.impl;

import org.le.base.rbac.permission.service.PermissionQuery;
import org.le.base.rbac.permission.service.cmd.CountPermissionCmd;
import org.le.base.rbac.permission.service.cmd.ListPermissionCmd;
import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.le.core.result.Page;
import org.le.model.AbstractQuery;
import org.le.model.OrderBy;
import org.le.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class PermissionQueryImpl extends AbstractQuery<PermissionQuery, PermissionEntity> implements PermissionQuery {

    private Long id;
    private Long pid;
    private String code;
    private String codeLike;
    private String operationLike;
    private String url;
    private String route;
    private Boolean menu;
    private CommandExecutor commandExecutor;

    public PermissionQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID("A.ID"), PID("A.PID"), CODE("A.CODE"), OPERATION("A.OPERATION"), URL("A.URL"), SORT("A.SORT");
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
    public PermissionQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public PermissionQuery pid(Long pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public PermissionQuery code(String code) {
        this.code = code;
        return this;
    }

    @Override
    public PermissionQuery codeLike(String codeLike) {
        this.codeLike = codeLike;
        return this;
    }

    @Override
    public PermissionQuery operationLike(String operationLike) {
        this.operationLike = operationLike.replaceAll("%", "/%").replaceAll("_", "/_");
        return this;
    }

    @Override
    public PermissionQuery url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PermissionQuery route(String route) {
        this.route = route;
        return this;
    }

    @Override
    public PermissionQuery isMenu(Boolean menu) {
        this.menu = menu;
        return this;
    }

    @Override
    public PermissionQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    public PermissionQuery orderByCode(Direction direction) {
        return orderBy(Query.CODE, direction);
    }

    @Override
    public PermissionQuery orderByOperation(Direction direction) {
        return orderBy(Query.OPERATION, direction);
    }

    @Override
    public PermissionQuery orderByUrl(Direction direction) {
        return orderBy(Query.URL, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountPermissionCmd(this));
    }

    @Override
    public PermissionQuery orderByPid(Direction direction) {
        return orderBy(Query.PID, direction);
    }

    @Override
    public PermissionQuery orderBySort(Direction direction) {
        return orderBy(Query.SORT, direction);
    }

    @Override
    protected List<PermissionEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListPermissionCmd(this));
    }
}
