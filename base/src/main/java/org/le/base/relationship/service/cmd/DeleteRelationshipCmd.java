package org.le.base.relationship.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.base.relationship.service.RelationshipType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class DeleteRelationshipCmd extends DeleteRelationshipBySourceIdCmd {

    protected Long targetId;

    public DeleteRelationshipCmd(Long sourceId, RelationshipType type, Long targetId) {
        super(sourceId, type);
        this.targetId = targetId;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        Map map = new HashMap(3);
        map.put("sourceId", sourceId);
        map.put("type", type);
        map.put("targetId", targetId);
        sqlSession.delete("org.le.base.relationship.delete", map);
        return null;
    }

}
