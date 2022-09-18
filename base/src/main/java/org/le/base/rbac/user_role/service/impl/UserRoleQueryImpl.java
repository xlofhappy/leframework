package org.le.base.rbac.user_role.service.impl;

import org.le.base.rbac.user_role.service.UserRoleQuery;
import org.le.base.rbac.user_role.service.cmd.CountUserRoleCmd;
import org.le.base.rbac.user_role.service.cmd.ListUserRoleCmd;
import org.le.base.rbac.user_role.service.po.UserRoleEntity;
import org.le.model.AbstractQuery;
import org.le.model.command.CommandExecutor;
import org.le.core.result.Page;
import org.le.model.OrderBy;

import java.util.List;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public class UserRoleQueryImpl extends AbstractQuery<UserRoleQuery, UserRoleEntity> implements UserRoleQuery {

    private Long userId;
    private Long roleId;
    private CommandExecutor commandExecutor;

    public UserRoleQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        UID("A.USER_ID"), ROLE_ID("A.ROLE_ID");
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
    public UserRoleQuery userId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public UserRoleQuery roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public UserRoleQuery orderByUid(Direction direction) {
        return orderBy(Query.UID, direction);
    }

    @Override
    public UserRoleQuery orderByRoleId(Direction direction) {
        return orderBy(Query.ROLE_ID, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountUserRoleCmd(this));
    }

    @Override
    protected List<UserRoleEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListUserRoleCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " " + Query.UID.getName() + " ";
    }
}
