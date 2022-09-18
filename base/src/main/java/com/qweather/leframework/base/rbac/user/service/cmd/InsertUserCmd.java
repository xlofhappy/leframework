package com.qweather.leframework.base.rbac.user.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class InsertUserCmd implements Command<Void> {

    protected UserEntity entity;

    public InsertUserCmd(UserEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getId() == null ) {
                entity.setId(Config.getDaoConfig().getIdPool().getNextId());
            }
            sqlSession.insert("com.qweather.leframework.base.rbac.user.insert", entity);
        }
        return null;
    }

}
