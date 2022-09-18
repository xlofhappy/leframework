package org.le.base.rbac.user.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class DeleteUserCmd implements Command<Void> {

    protected Long id;

    public DeleteUserCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("org.le.base.rbac.user.delete", id);
        }
        return null;
    }

}
