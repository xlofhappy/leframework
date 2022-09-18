package org.le.base.rbac.permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.permission.service.impl.PermissionQueryImpl;
import org.le.base.rbac.permission.service.po.PermissionEntity;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class ListPermissionCmd implements Command<List<PermissionEntity>> {

    protected PermissionQueryImpl permissionQueryImpl;

    public ListPermissionCmd(PermissionQueryImpl permissionQueryImpl) {
        this.permissionQueryImpl = permissionQueryImpl;
    }

    @Override
    public List<PermissionEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.permission.list", permissionQueryImpl);
    }

}
