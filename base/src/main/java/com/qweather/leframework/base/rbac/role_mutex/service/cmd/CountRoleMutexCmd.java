package com.qweather.leframework.base.rbac.role_mutex.service.cmd;


import com.qweather.leframework.base.rbac.role_mutex.service.impl.RoleMutexQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
