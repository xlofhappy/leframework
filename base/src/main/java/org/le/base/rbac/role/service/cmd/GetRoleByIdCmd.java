package org.le.base.rbac.role.service.cmd;


import org.le.base.rbac.role.service.po.RoleEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class GetRoleByIdCmd implements Command<RoleEntity> {

    protected Long id;

    public GetRoleByIdCmd(Long id) {
        this.id = id;
    }

    @Override
    public RoleEntity execute(SqlSession sqlSession) {
        if ( id != null ) {
            return sqlSession.selectOne("org.le.base.rbac.role.getById", id);
        }
        return null;
    }

}
