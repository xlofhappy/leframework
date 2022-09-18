package com.qweather.leframework.base.rbac.role_permission.service.cmd;


import com.qweather.leframework.base.rbac.role_permission.service.po.RolePermissionEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class InsertRolePermissionCmd implements Command<Void> {

    protected RolePermissionEntity entity;

    public InsertRolePermissionCmd(RolePermissionEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null && entity.getPermissionId() != null && entity.getRoleId() != null ) {
            sqlSession.insert("com.qweather.leframework.base.rbac.role_permission.insert", entity);
        }
        return null;
    }

}
