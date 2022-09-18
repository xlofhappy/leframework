package org.le.base.rbac.role_permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-11-08 14:34:13
 *
 * @author xiaole
 */
public class DeleteRolePermissionByRoleIdCmd implements Command<Void> {

    protected Long roleId;

    public DeleteRolePermissionByRoleIdCmd(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( roleId != null) {
            sqlSession.delete("org.le.base.rbac.role_permission.deleteByRoleId", roleId);
        }
        return null;
    }

}
