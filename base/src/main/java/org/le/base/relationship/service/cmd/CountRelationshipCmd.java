package org.le.base.relationship.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.base.relationship.service.impl.RelationshipQueryImpl;
import org.le.model.command.Command;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class CountRelationshipCmd implements Command<Long> {

    protected RelationshipQueryImpl relationshipQuery;

    public CountRelationshipCmd(RelationshipQueryImpl relationshipQuery) {
        this.relationshipQuery = relationshipQuery;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.relationship.count", relationshipQuery);
    }

}
