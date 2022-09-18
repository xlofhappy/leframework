package org.le.base.rbac.permission.service.cmd;


import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2019-03-03 11:48:13
 *
 * @author xiaole
 */
public class UpdatePermissionCmd implements Command<Void> {

    protected PermissionEntity entity;

    public UpdatePermissionCmd(PermissionEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null && entity.getId() != null ) {
            sqlSession.update("org.le.base.rbac.permission.update", entity);
        }
        return null;
    }

}
