package com.qweather.leframework.base.rbac.role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class DeleteRoleCmd implements Command<Void> {

    protected Long id;

    public DeleteRoleCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.update("org.le.base.rbac.role.delete", id);
        }
        return null;
    }

}
