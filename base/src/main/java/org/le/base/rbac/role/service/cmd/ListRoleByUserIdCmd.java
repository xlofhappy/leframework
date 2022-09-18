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
public class ListRoleByUserIdCmd implements Command<List<RoleEntity>> {

    protected Long userId;

    public ListRoleByUserIdCmd(Long userId) {
        this.userId = userId;
    }

    @Override
    public List<RoleEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.role.getUserRoleList", userId);
    }

}
