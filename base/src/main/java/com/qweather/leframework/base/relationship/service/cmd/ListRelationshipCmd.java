package com.qweather.leframework.base.relationship.service.cmd;


import com.qweather.leframework.base.relationship.service.impl.RelationshipQueryImpl;
import com.qweather.leframework.base.relationship.service.po.RelationshipEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
        return sqlSession.selectList("com.qweather.leframework.base.relationship.list", relationshipQuery);
    }

}
