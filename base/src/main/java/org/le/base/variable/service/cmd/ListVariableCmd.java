package org.le.base.variable.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.variable.service.impl.VariableQueryImpl;
import org.le.base.variable.service.po.VariableEntity;
import java.util.List;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class ListVariableCmd implements Command<List<VariableEntity>> {

    protected VariableQueryImpl variableQueryImpl;

    public ListVariableCmd(VariableQueryImpl variableQueryImpl) {
        this.variableQueryImpl = variableQueryImpl;
    }

    @Override
    public List<VariableEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.variable.list", variableQueryImpl);
    }

}
