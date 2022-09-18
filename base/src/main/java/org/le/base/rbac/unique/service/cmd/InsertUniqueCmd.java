package org.le.base.rbac.unique.service.cmd;


import org.le.base.rbac.unique.service.po.UniqueEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

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
