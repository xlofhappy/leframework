package org.le.base.rbac.role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.core.config.Config;
import org.le.base.rbac.role.service.po.RoleEntity;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class InsertRoleCmd implements Command<Void> {

    protected RoleEntity entity;

    public InsertRoleCmd(RoleEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getId() == null ) {
                entity.setId(Config.getDaoConfig().getIdPool().getNextId());
            }
            sqlSession.insert("org.le.base.rbac.role.insert", entity);
        }
        return null;
    }

}
