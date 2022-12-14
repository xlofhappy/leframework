package com.qweather.leframework.base.rbac.role_permission.service.impl;

import com.qweather.leframework.base.rbac.role_permission.service.cmd.CountRolePermissionCmd;
import com.qweather.leframework.base.rbac.role_permission.service.cmd.ListRolePermissionCmd;
import com.qweather.leframework.base.rbac.role_permission.service.RolePermissionQuery;
import com.qweather.leframework.base.rbac.role_permission.service.po.RolePermissionEntity;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class RolePermissionQueryImpl extends AbstractQuery<RolePermissionQuery, RolePermissionEntity> implements RolePermissionQuery {


    private Long roleId;
    private Long permissionId;
    private CommandExecutor  commandExecutor;

    public RolePermissionQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ROLE_ID("A.ROLE_ID"), PERMISSION_ID("A.PERMISSION_ID");
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
    public RolePermissionQuery roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public RolePermissionQuery permissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    @Override
    public RolePermissionQuery orderByRoleId(Direction direction) {
        return orderBy(Query.ROLE_ID, direction);
    }

    @Override
    public RolePermissionQuery orderByPermissionId(Direction direction) {
        return orderBy(Query.PERMISSION_ID, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountRolePermissionCmd(this));
    }

    @Override
    protected List<RolePermissionEntity> executeList(Page page) {
        if ( page != null ) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListRolePermissionCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " A.ROLE_ID ";
    }
}
