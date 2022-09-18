package org.le.base.rbac.role.service.cmd;


import org.le.base.rbac.role.service.po.RoleEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class UpdateRoleCmd implements Command<Void> {

    protected RoleEntity entity;

    public UpdateRoleCmd(RoleEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null && entity.getId() != null ) {
            sqlSession.update("org.le.base.rbac.role.update", entity);
        }
        return null;
    }

}
