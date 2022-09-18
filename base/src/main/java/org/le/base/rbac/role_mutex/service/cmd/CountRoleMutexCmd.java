package org.le.base.rbac.role_mutex.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.role_mutex.service.impl.RoleMutexQueryImpl;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public class CountRoleMutexCmd implements Command<Long> {

    protected RoleMutexQueryImpl roleMutexQueryImpl;

    public CountRoleMutexCmd(RoleMutexQueryImpl roleMutexQueryImpl) {
        this.roleMutexQueryImpl = roleMutexQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.rbac.role_mutex.count", roleMutexQueryImpl);
    }

}
