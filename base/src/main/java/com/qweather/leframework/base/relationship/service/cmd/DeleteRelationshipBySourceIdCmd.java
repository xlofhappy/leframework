package com.qweather.leframework.base.relationship.service.cmd;

import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.base.relationship.service.RelationshipType;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class DeleteRelationshipBySourceIdCmd implements Command<Void> {

    protected Long sourceId;
    protected RelationshipType type;

    public DeleteRelationshipBySourceIdCmd(Long sourceId, RelationshipType type) {
        this.sourceId = sourceId;
        this.type = type;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        Map map = new HashMap(2);
        map.put("sourceId", sourceId);
        map.put("type", type.getType());
        sqlSession.delete("com.qweather.leframework.base.relationship.deleteBySourceId", map);
        return null;
    }
}
