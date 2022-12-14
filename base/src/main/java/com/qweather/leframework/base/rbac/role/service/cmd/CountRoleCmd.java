package com.qweather.leframework.base.rbac.role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.base.rbac.role.service.impl.RoleQueryImpl;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class CountRoleCmd implements Command<Long> {

    protected RoleQueryImpl roleQueryImpl;

    public CountRoleCmd(RoleQueryImpl roleQueryImpl) {
        this.roleQueryImpl = roleQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("com.qweather.leframework.base.rbac.role.count", roleQueryImpl);
    }

}
