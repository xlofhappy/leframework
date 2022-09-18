package org.le.base.rbac.permission.service.cmd;


import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class GetPermissionCmd implements Command<PermissionEntity> {

    protected Long id;

    public GetPermissionCmd(Long id) {
        this.id = id;
    }

    @Override
    public PermissionEntity execute(SqlSession sqlSession) {
        if ( id != null ) {
            return sqlSession.selectOne("org.le.base.rbac.permission.getById", new PermissionEntity(id));
        }
        return null;
    }

}
