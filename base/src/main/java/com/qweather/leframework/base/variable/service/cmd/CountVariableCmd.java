package com.qweather.leframework.base.variable.service.cmd;


import com.qweather.leframework.base.variable.service.impl.VariableQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class CountVariableCmd implements Command<Long> {

    protected VariableQueryImpl variableQueryImpl;

    public CountVariableCmd(VariableQueryImpl variableQueryImpl) {
        this.variableQueryImpl = variableQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("com.qweather.leframework.base.variable.count", variableQueryImpl);
    }

}
