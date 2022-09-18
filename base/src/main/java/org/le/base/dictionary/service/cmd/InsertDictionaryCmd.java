package org.le.base.dictionary.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.core.config.Config;
import org.le.base.dictionary.service.po.DictionaryEntity;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class InsertDictionaryCmd implements Command<Void> {

    protected DictionaryEntity entity;

    public InsertDictionaryCmd(DictionaryEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getId() == null ) {
                entity.setId(Config.getDaoConfig().getIdPool().getNextId());
            }
            sqlSession.insert("org.le.base.dictionary.insert", entity);
        }
        return null;
    }

}
