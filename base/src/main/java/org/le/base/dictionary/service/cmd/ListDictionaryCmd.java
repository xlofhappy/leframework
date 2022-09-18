package org.le.base.dictionary.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.dictionary.service.impl.DictionaryQueryImpl;
import org.le.base.dictionary.service.po.DictionaryEntity;
import java.util.List;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class ListDictionaryCmd implements Command<List<DictionaryEntity>> {

    protected DictionaryQueryImpl dictionaryQueryImpl;

    public ListDictionaryCmd(DictionaryQueryImpl dictionaryQueryImpl) {
        this.dictionaryQueryImpl = dictionaryQueryImpl;
    }

    @Override
    public List<DictionaryEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.dictionary.list", dictionaryQueryImpl);
    }

}
