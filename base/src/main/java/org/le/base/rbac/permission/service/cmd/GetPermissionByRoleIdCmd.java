package org.le.base.rbac.permission.service.cmd;


import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class GetPermissionByRoleIdCmd implements Command<List<PermissionEntity>> {

    protected Long roleId;

    public GetPermissionByRoleIdCmd(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public List<PermissionEntity> execute(SqlSession sqlSession) {
        if ( roleId != null ) {
            return sqlSession.selectList("org.le.base.rbac.permission.getPermissionByRoleId", roleId);
        }
        return null;
    }

}
