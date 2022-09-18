package org.le.base.dictionary.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class DeleteDictionaryCmd implements Command<Void> {

    protected Long id;

    public DeleteDictionaryCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.update("org.le.base.dictionary.delete", id);
        }
        return null;
    }

}
