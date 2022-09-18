package org.le.base.variable.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class DeleteVariableForeverCmd implements Command<Void> {

    protected Long id;

    public DeleteVariableForeverCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("org.le.base.variable.deleteForever", id);
        }
        return null;
    }

}
