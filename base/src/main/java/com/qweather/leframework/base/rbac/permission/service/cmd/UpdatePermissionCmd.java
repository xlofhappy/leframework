package com.qweather.leframework.base.rbac.permission.service.cmd;


import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
            sqlSession.update("com.qweather.leframework.base.rbac.permission.update", entity);
        }
        return null;
    }

}
