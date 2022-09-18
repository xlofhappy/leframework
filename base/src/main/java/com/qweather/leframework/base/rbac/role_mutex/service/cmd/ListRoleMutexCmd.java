package com.qweather.leframework.base.rbac.role_mutex.service.cmd;


import com.qweather.leframework.base.rbac.role_mutex.service.impl.RoleMutexQueryImpl;
import com.qweather.leframework.base.rbac.role_mutex.service.po.RoleMutexEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
        return sqlSession.selectList("com.qweather.leframework.base.rbac.role_mutex.list", roleMutexQueryImpl);
    }

}
