package org.le.base.rbac.user_extend.service.impl;

import org.le.base.rbac.user_extend.service.UserExtend;
import org.le.base.rbac.user_extend.service.UserExtendQuery;
import org.le.base.rbac.user_extend.service.cmd.CountUserExtendCmd;
import org.le.base.rbac.user_extend.service.cmd.ListUserExtendCmd;
import org.le.base.rbac.user_extend.service.po.UserExtendEntity;
import org.le.core.result.Page;
import org.le.model.AbstractQuery;
import org.le.model.OrderBy;
import org.le.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-03 15:19:52
 *
 * @author xiaole
 */
public class UserExtendQueryImpl extends AbstractQuery<UserExtendQuery, UserExtendEntity> implements UserExtendQuery {

    private Long uid;
    private Integer k;
    private String v;
    private final CommandExecutor commandExecutor;

    public UserExtendQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        UID("A.UID"), K("A.K"), V("A.V");
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
    public UserExtendQuery uid(Long uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public UserExtendQuery k(UserExtend k) {
        this.k = k.getK();
        return this;
    }

    @Override
    public UserExtendQuery v(String v) {
        this.v = v;
        return this;
    }

    @Override
    public UserExtendQuery orderByUid(Direction direction) {
        return orderBy(Query.UID, direction);
    }

    @Override
    public UserExtendQuery orderByK(Direction direction) {
        return orderBy(Query.K, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountUserExtendCmd(this));
    }

    @Override
    protected List<UserExtendEntity> executeList(Page page) {
        if ( page != null ) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListUserExtendCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " A.UID, A.K ";
    }
}
