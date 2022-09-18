package org.le.base.rbac.permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.permission.service.impl.PermissionQueryImpl;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class CountPermissionCmd implements Command<Long> {

    protected PermissionQueryImpl permissionQueryImpl;

    public CountPermissionCmd(PermissionQueryImpl permissionQueryImpl) {
        this.permissionQueryImpl = permissionQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.rbac.permission.count", permissionQueryImpl);
    }

}
