package com.qweather.leframework.base.dictionary.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class DeleteDictionaryForeverCmd implements Command<Void> {

    protected Long id;

    public DeleteDictionaryForeverCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("com.qweather.leframework.base.dictionary.deleteForever", id);
        }
        return null;
    }

}
