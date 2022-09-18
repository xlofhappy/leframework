package org.le.base.rbac.role_permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.role_permission.service.impl.RolePermissionQueryImpl;
import org.le.base.rbac.role_permission.service.po.RolePermissionEntity;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class ListRolePermissionCmd implements Command<List<RolePermissionEntity>> {

    protected RolePermissionQueryImpl role_permissionQueryImpl;

    public ListRolePermissionCmd(RolePermissionQueryImpl role_permissionQueryImpl) {
        this.role_permissionQueryImpl = role_permissionQueryImpl;
    }

    @Override
    public List<RolePermissionEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.role_permission.list", role_permissionQueryImpl);
    }

}
