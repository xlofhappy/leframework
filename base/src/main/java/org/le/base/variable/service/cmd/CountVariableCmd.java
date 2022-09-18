package org.le.base.variable.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.variable.service.impl.VariableQueryImpl;

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
        return sqlSession.selectOne("org.le.base.variable.count", variableQueryImpl);
    }

}
