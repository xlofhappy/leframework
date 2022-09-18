package org.le.base.rbac.role_permission.service.cmd;


import org.le.base.rbac.role_permission.service.po.RolePermissionEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class MultiInsertRolePermissionCmd implements Command<Void> {

    protected List<RolePermissionEntity> entityList;

    public MultiInsertRolePermissionCmd(List<RolePermissionEntity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entityList != null && entityList.size() > 0 ) {
            sqlSession.delete("org.le.base.rbac.role_permission.multiInsert", entityList);
        }
        return null;
    }

}
