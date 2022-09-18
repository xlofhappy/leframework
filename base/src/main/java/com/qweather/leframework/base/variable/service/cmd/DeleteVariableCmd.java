package com.qweather.leframework.base.variable.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class DeleteVariableCmd implements Command<Void> {

    protected Long id;

    public DeleteVariableCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("com.qweather.leframework.base.variable.delete", id);
        }
        return null;
    }

}
