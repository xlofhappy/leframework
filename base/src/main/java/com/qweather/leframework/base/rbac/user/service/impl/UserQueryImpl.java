package com.qweather.leframework.base.rbac.user.service.impl;

import com.qweather.leframework.base.rbac.unique.service.UniqueType;
import com.qweather.leframework.base.rbac.user.service.cmd.CountUserCmd;
import com.qweather.leframework.base.rbac.user.service.cmd.ListUserCmd;
import com.qweather.leframework.base.rbac.user.service.UserQuery;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class UserQueryImpl extends AbstractQuery<UserQuery, UserEntity> implements UserQuery {

    private Long id;
    private Long authorId;
    private String authorTime;
    private String authorTimeLike;
    private String authorTimeAfter;
    private Long modifyerId;
    private String modifyTime;
    private Boolean display;
    private String unionId;
    private String unionIdLike;
    private String type;
    private String nickname;
    private String realname;
    private String username;
    private String phone;
    private String country;
    private Long roleId;
    private String email;
    private Short status;
    private String emailLike;
    private Long[] idIn;
    private Long[] inRoles;
    private Long idNotEquals;
    private Long idGreaterOrEqual;
    private CommandExecutor commandExecutor;

    public UserQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID("A.ID"), EMAIL("A.EMAIL"), USERNAME("A.USERNAME"), PASSWORD("A.PASSWORD"), SALT("A.SALT"), NICKNAME("A.NICKNAME"),
        REALNAME("A.REALNAME"), SEX("A.SEX"), PHONE("A.PHONE"), COUNTRY("A.COUNTRY"), BIRTHDAY("A.BIRTHDAY"),
        AUTHOR_ID("A.AUTHOR_ID"), AUTHOR_TIME("A.AUTHOR_TIME"), MODIFYER_ID("A.MODIFYER_ID"), MODIFY_TIME("A.MODIFY_TIME");
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
    public UserQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public UserQuery idIn(Long... ids) {
        this.idIn = ids;
        return this;
    }

    @Override
    public UserQuery idNotEquals(Long id) {
        this.idNotEquals = id;
        return this;
    }

    @Override
    public UserQuery idGreaterOrEqual(Long id) {
        this.idGreaterOrEqual = id;
        return this;
    }

    @Override
    public UserQuery unionId(String unionId, UniqueType type) {
        this.unionId = unionId;
        this.type = type.type();
        return this;
    }

    @Override
    public UserQuery unionIdLike(String unionIdLike, UniqueType type) {
        this.unionIdLike = unionIdLike.replaceAll("%", "/%").replaceAll("_", "/_");
        this.type = type.type();
        return this;
    }

    @Override
    public UserQuery unionId(String unionId) {
        this.unionId = unionId;
        return this;
    }

    @Override
    public UserQuery unionIdLike(String unionIdLike) {
        this.unionIdLike = unionIdLike.replaceAll("%", "/%").replaceAll("_", "/_");
        return this;
    }

    @Override
    public UserQuery authorTimeLike(String authorTimeLike) {
        this.authorTimeLike = authorTimeLike.replaceAll("%", "/%").replaceAll("_", "/_");
        return this;
    }

    @Override
    public UserQuery authorTimeAfter(String authorTimeAfter) {
        this.authorTimeAfter = authorTimeAfter;
        return this;
    }

    @Override
    public UserQuery type(UniqueType type) {
        this.type = type.type();
        return this;
    }

    @Override
    public UserQuery nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Override
    public UserQuery realname(String realname) {
        this.realname = realname;
        return this;
    }

    @Override
    public UserQuery phone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public UserQuery emailLike(String emailLike) {
        this.emailLike = emailLike.replaceAll("%", "/%").replaceAll("_", "/_");
        return this;
    }

    @Override
    public UserQuery email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserQuery status(Short status) {
        this.status = status;
        return this;
    }

    @Override
    public UserQuery country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public UserQuery roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public UserQuery inRoles(Long... roleId) {
        this.inRoles = roleId;
        return this;
    }

    @Override
    public UserQuery username(String username) {
        this.username = username;
        return this;
    }

    @Override
    public UserQuery authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    @Override
    public UserQuery modifyerId(Long modifyerId) {
        this.modifyerId = modifyerId;
        return this;
    }

    @Override
    public UserQuery display(Boolean display) {
        this.display = display;
        return this;
    }

    @Override
    public UserQuery orderByAuthorTime(Direction direction) {
        return orderBy(Query.AUTHOR_TIME, direction);
    }

    @Override
    public UserQuery orderByModifyTime(Direction direction) {
        return orderBy(Query.MODIFY_TIME, direction);
    }

    @Override
    public UserQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    public UserQuery orderByEmail(Direction direction) {
        return orderBy(Query.EMAIL, direction);
    }

    @Override
    public UserQuery orderByCountry(Direction direction) {
        return orderBy(Query.COUNTRY, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountUserCmd(this));
    }

    @Override
    protected List<UserEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListUserCmd(this));
    }
}
