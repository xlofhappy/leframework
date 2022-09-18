package com.qweather.leframework.base.rbac.role.service.cmd;


import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.core.config.Config;

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
