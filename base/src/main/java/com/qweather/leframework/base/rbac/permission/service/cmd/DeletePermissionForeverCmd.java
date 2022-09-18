package com.qweather.leframework.base.rbac.permission.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class DeletePermissionForeverCmd implements Command<Void> {

    protected String id;

    public DeletePermissionForeverCmd(String id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("org.le.base.rbac.permission.deleteForever", id);
        }
        return null;
    }

}
