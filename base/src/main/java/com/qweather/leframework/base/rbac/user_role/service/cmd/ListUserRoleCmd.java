package com.qweather.leframework.base.rbac.user_role.service.cmd;


import com.qweather.leframework.base.rbac.user_role.service.po.UserRoleEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.base.rbac.user_role.service.impl.UserRoleQueryImpl;

import java.util.List;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public class ListUserRoleCmd implements Command<List<UserRoleEntity>> {

    protected UserRoleQueryImpl userRoleQueryImpl;

    public ListUserRoleCmd(UserRoleQueryImpl userRoleQueryImpl) {
        this.userRoleQueryImpl = userRoleQueryImpl;
    }

    @Override
    public List<UserRoleEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("com.qweather.leframework.base.rbac.user_role.list", userRoleQueryImpl);
    }

}
