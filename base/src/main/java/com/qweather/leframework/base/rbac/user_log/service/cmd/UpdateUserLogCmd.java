package com.qweather.leframework.base.rbac.user_log.service.cmd;

import com.qweather.leframework.base.rbac.user_log.service.po.UserLogEntity;
import com.qweather.leframework.model.command.Command;
import org.apache.ibatis.session.SqlSession;

/**
 * Created at 2022-11-07T15:56:56+08:00
 * Generated by Le Generator, created by xiaole
 *
 * @author dongjunchen
 */
public class UpdateUserLogCmd implements Command<Void> {

    protected UserLogEntity entity;

    public UpdateUserLogCmd(UserLogEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity.getId() != null ) {
            sqlSession.update("com.qweather.leframework.base.rbac.user_log.update", entity);
        }
        return null;
    }

}
