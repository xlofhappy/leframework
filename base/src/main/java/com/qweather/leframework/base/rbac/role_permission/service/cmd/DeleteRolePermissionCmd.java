package com.qweather.leframework.base.rbac.role_permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class DeleteRolePermissionCmd implements Command<Void> {

    protected Long roleId;
    protected Long permissionId;

    public DeleteRolePermissionCmd(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( permissionId != null ) {
            Map map = new HashMap<>(2);
            map.put("roleId", roleId);
            map.put("permissionId", permissionId);
            sqlSession.delete("org.le.base.rbac.role_permission.delete", map);
        }
        return null;
    }

}
