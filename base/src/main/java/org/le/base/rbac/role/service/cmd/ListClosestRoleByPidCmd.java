package org.le.base.rbac.role.service.cmd;


import org.le.base.rbac.role.service.po.RoleEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.List;

/**
 * Created at 2018-11-08 14:34:13
 *
 * @author xiaole
 */
public class ListClosestRoleByPidCmd implements Command<List<RoleEntity>> {

    protected Long pid;

    public ListClosestRoleByPidCmd(Long pid) {
        this.pid = pid;
    }

    @Override
    public List<RoleEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.role.getClosestChildren", pid);
    }

}
