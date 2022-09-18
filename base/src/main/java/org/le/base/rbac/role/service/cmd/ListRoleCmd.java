package org.le.base.rbac.role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.role.service.impl.RoleQueryImpl;
import org.le.base.rbac.role.service.po.RoleEntity;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class ListRoleCmd implements Command<List<RoleEntity>> {

    protected RoleQueryImpl roleQueryImpl;

    public ListRoleCmd(RoleQueryImpl roleQueryImpl) {
        this.roleQueryImpl = roleQueryImpl;
    }

    @Override
    public List<RoleEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.role.list", roleQueryImpl);
    }

}
