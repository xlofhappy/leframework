package org.le.base.rbac.role.service.cmd;


import org.le.base.rbac.role.service.po.RoleEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class ListRoleMutexByIdCmd implements Command<List<RoleEntity>> {

    protected Long id;

    public ListRoleMutexByIdCmd(Long id) {
        this.id = id;
    }

    @Override
    public List<RoleEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.role.getMutexById", id);
    }

}
