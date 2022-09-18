package com.qweather.leframework.base.rbac.user_extend.service.cmd;


import com.qweather.leframework.base.rbac.user_extend.service.po.UserExtendEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public class InsertUserExtendCmd implements Command<Void> {

    protected UserExtendEntity entity;

    public InsertUserExtendCmd(UserExtendEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getUid() != null && entity.getK() != null ) {
                sqlSession.insert("org.le.base.rbac.user_extend.insert", entity);
            }
        }
        return null;
    }

}
