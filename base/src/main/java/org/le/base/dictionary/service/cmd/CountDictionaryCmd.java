package org.le.base.dictionary.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.dictionary.service.impl.DictionaryQueryImpl;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class CountDictionaryCmd implements Command<Long> {

    protected DictionaryQueryImpl dictionaryQueryImpl;

    public CountDictionaryCmd(DictionaryQueryImpl dictionaryQueryImpl) {
        this.dictionaryQueryImpl = dictionaryQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.dictionary.count", dictionaryQueryImpl);
    }

}
