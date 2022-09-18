package org.le.base.dictionary.service.cmd;


import org.le.base.dictionary.service.po.DictionaryEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class UpdateDictionaryCmd implements Command<Void> {

    protected DictionaryEntity entity;

    public UpdateDictionaryCmd(DictionaryEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            sqlSession.update("org.le.base.dictionary.update", entity);
        }
        return null;
    }

}
