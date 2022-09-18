package org.le.base.relationship.service.cmd;


import org.le.base.relationship.service.impl.RelationshipQueryImpl;
import org.le.base.relationship.service.po.RelationshipEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.List;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class ListRelationshipCmd implements Command<List<RelationshipEntity>> {

    protected RelationshipQueryImpl relationshipQuery;

    public ListRelationshipCmd(RelationshipQueryImpl relationshipQuery) {
        this.relationshipQuery = relationshipQuery;
    }

    @Override
    public List<RelationshipEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.relationship.list", relationshipQuery);
    }

}
