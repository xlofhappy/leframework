package com.qweather.leframework.base.rbac.unique.service.cmd;


import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class InsertUniqueCmd implements Command<Void> {

    protected UniqueEntity entity;

    public InsertUniqueCmd(UniqueEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getUnionId() != null && entity.getType() != null && entity.getUid() != null ) {
                sqlSession.insert("org.le.base.rbac.unique.insert", entity);
            }
        }
        return null;
    }

}
