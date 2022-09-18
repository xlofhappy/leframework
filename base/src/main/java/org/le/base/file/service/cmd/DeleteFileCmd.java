package org.le.base.file.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class DeleteFileCmd implements Command<Void> {

    protected Long id;

    public DeleteFileCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.update("org.le.base.file.delete", id);
        }
        return null;
    }

}
