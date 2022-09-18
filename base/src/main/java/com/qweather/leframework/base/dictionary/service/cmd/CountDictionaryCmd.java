package com.qweather.leframework.base.dictionary.service.cmd;


import com.qweather.leframework.base.dictionary.service.impl.DictionaryQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
