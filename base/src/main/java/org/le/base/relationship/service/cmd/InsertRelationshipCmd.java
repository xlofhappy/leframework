package org.le.base.relationship.service.cmd;


import org.le.base.relationship.service.po.RelationshipEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class InsertRelationshipCmd implements Command<Void> {

    protected RelationshipEntity entity;

    public InsertRelationshipCmd(RelationshipEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            sqlSession.insert("org.le.base.relationship.insert", entity);
        }
        return null;
    }

}
