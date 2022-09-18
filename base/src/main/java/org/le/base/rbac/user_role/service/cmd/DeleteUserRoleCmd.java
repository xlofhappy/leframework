package org.le.base.rbac.user_role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public class DeleteUserRoleCmd implements Command<Void> {

    protected Long userId;
    protected Long roleId;

    public DeleteUserRoleCmd(Long userId) {
        this.userId = userId;
    }

    public DeleteUserRoleCmd(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (userId != null) {
            Map map = new HashMap<>(2);
            map.put("userId", userId);
            if (roleId != null) {
                map.put("roleId", roleId);
            }
            sqlSession.delete("org.le.base.rbac.user_role.delete", map);
        }
        return null;
    }

}
