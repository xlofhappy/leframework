package org.le.base.rbac.user.service.cmd;


import org.le.base.rbac.user.service.po.UserEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class UpdateUserCmd implements Command<Void> {

    protected UserEntity entity;

    public UpdateUserCmd(UserEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null && entity.getId() != null ) {
            sqlSession.insert("org.le.base.rbac.user.update", entity);
        }
        return null;
    }

}
