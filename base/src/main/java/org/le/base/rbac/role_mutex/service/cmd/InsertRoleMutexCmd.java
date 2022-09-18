package org.le.base.rbac.role_mutex.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.core.config.Config;
import org.le.base.rbac.role_mutex.service.po.RoleMutexEntity;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public class InsertRoleMutexCmd implements Command<Void> {

    protected RoleMutexEntity entity;

    public InsertRoleMutexCmd(RoleMutexEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getId() == null ) {
                entity.setId(Config.getDaoConfig().getIdPool().getNextId());
            }
            sqlSession.insert("org.le.base.rbac.role_mutex.insert", entity);
        }
        return null;
    }

}
