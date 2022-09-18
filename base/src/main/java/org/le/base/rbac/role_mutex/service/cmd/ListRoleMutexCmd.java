package org.le.base.rbac.role_mutex.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.role_mutex.service.impl.RoleMutexQueryImpl;
import org.le.base.rbac.role_mutex.service.po.RoleMutexEntity;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public class ListRoleMutexCmd implements Command<List<RoleMutexEntity>> {

    protected RoleMutexQueryImpl roleMutexQueryImpl;

    public ListRoleMutexCmd(RoleMutexQueryImpl roleMutexQueryImpl) {
        this.roleMutexQueryImpl = roleMutexQueryImpl;
    }

    @Override
    public List<RoleMutexEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.role_mutex.list", roleMutexQueryImpl);
    }

}
