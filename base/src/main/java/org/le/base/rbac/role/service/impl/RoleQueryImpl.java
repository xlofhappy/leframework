package org.le.base.rbac.role.service.impl;

import org.le.base.rbac.role.service.RoleQuery;
import org.le.base.rbac.role.service.cmd.CountRoleCmd;
import org.le.base.rbac.role.service.cmd.ListRoleCmd;
import org.le.base.rbac.role.service.po.RoleEntity;
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
public class RoleQueryImpl extends AbstractQuery<RoleQuery, RoleEntity> implements RoleQuery {

    private Long id;
    private Long pid;
    private String code;
    private String name;
    private Integer permissionLimit;
    private Long needRoleId;
    private CommandExecutor commandExecutor;

    public RoleQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID("A.ID"), PID("A.PID"), CODE("A.CODE"), NAME("A.NAME"), PERMISSION_LIMIT("A.PERMISSION_LIMIT"), NEED_ROLE_ID("A.NEED_ROLE_ID");

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
    public RoleQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public RoleQuery pid(Long pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public RoleQuery code(String code) {
        this.code = code;
        return this;
    }

    @Override
    public RoleQuery name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public RoleQuery permissionLimit(Integer permissionLimit) {
        this.permissionLimit = permissionLimit;
        return this;
    }

    @Override
    public RoleQuery needRoleId(Long needRoleId) {
        this.needRoleId = needRoleId;
        return this;
    }

    @Override
    public RoleQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    public RoleQuery orderByPid(Direction direction) {
        return orderBy(Query.PID, direction);
    }

    @Override
    public RoleQuery orderByCode(Direction direction) {
        return orderBy(Query.CODE, direction);
    }

    @Override
    public RoleQuery orderByName(Direction direction) {
        return orderBy(Query.NAME, direction);
    }

    @Override
    public RoleQuery orderByPermissionLimit(Direction direction) {
        return orderBy(Query.PERMISSION_LIMIT, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountRoleCmd(this));
    }

    @Override
    protected List<RoleEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListRoleCmd(this));
    }
}
