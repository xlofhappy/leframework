package com.qweather.leframework.base.variable.service.cmd;


import com.qweather.leframework.base.variable.service.po.VariableEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class UpdateVariableCmd implements Command<Void> {

    protected VariableEntity entity;

    public UpdateVariableCmd(VariableEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            sqlSession.update("org.le.base.variable.update", entity);
        }
        return null;
    }

}
