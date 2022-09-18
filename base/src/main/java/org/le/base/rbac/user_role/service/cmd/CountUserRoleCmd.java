package org.le.base.rbac.user_role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.user_role.service.impl.UserRoleQueryImpl;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public class CountUserRoleCmd implements Command<Long> {

    protected UserRoleQueryImpl userRoleQueryImpl;

    public CountUserRoleCmd(UserRoleQueryImpl userRoleQueryImpl) {
        this.userRoleQueryImpl = userRoleQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.rbac.user_role.count", userRoleQueryImpl);
    }

}
