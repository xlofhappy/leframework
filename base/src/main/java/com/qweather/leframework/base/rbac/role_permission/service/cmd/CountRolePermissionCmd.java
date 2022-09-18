package com.qweather.leframework.base.rbac.role_permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.base.rbac.role_permission.service.impl.RolePermissionQueryImpl;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class CountRolePermissionCmd implements Command<Long> {

    protected RolePermissionQueryImpl rolePermissionQueryImpl;

    public CountRolePermissionCmd(RolePermissionQueryImpl rolePermissionQueryImpl) {
        this.rolePermissionQueryImpl = rolePermissionQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("com.qweather.leframework.base.rbac.role_permission.count", rolePermissionQueryImpl);
    }

}
