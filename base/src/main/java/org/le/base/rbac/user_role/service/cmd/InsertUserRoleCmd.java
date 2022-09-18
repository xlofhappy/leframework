package org.le.base.rbac.user_role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.base.rbac.user_role.service.po.UserRoleEntity;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public class InsertUserRoleCmd implements Command<Void> {

    protected UserRoleEntity entity;

    public InsertUserRoleCmd(UserRoleEntity entity) {
        this.entity = entity;
    }

    public InsertUserRoleCmd(Long userId, Long roleId) {
        this.entity = new UserRoleEntity();
        this.entity.setUserId(userId);
        this.entity.setRoleId(roleId);
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (entity != null && entity.getRoleId() != null && entity.getUserId() != null) {
            sqlSession.delete("org.le.base.rbac.user_role.insert", entity);
        }
        return null;
    }

}
