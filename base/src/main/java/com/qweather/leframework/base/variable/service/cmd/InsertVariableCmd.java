package com.qweather.leframework.base.variable.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.base.variable.service.po.VariableEntity;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class InsertVariableCmd implements Command<Void> {

    protected VariableEntity entity;

    public InsertVariableCmd(VariableEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getId() == null ) {
                entity.setId(Config.getDaoConfig().getIdPool().getNextId());
            }
            sqlSession.insert("org.le.base.variable.insert", entity);
        }
        return null;
    }

}
