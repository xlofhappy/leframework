package com.qweather.leframework.base.rbac.role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class DeleteRoleForeverCmd implements Command<Void> {

    protected Long id;

    public DeleteRoleForeverCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("com.qweather.leframework.base.rbac.role.deleteForever", id);
        }
        return null;
    }

}
