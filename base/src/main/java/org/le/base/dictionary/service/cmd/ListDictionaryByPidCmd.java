package org.le.base.dictionary.service.cmd;


import org.le.base.dictionary.service.po.DictionaryEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.List;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class ListDictionaryByPidCmd implements Command<List<DictionaryEntity>> {

    protected Long pid;

    public ListDictionaryByPidCmd(Long pid) {
        this.pid = pid;
    }

    @Override
    public List<DictionaryEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.dictionary.getClosestChildren", pid);
    }

}
