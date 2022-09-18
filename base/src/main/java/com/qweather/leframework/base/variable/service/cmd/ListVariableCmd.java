package com.qweather.leframework.base.variable.service.cmd;


import com.qweather.leframework.base.variable.service.impl.VariableQueryImpl;
import com.qweather.leframework.base.variable.service.po.VariableEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
