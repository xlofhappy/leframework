package com.qweather.leframework.base.relationship.service.cmd;


import com.qweather.leframework.base.relationship.service.impl.RelationshipQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
        return sqlSession.selectOne("com.qweather.leframework.base.relationship.count", relationshipQuery);
    }

}
