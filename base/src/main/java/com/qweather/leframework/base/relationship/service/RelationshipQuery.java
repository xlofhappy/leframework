package com.qweather.leframework.base.relationship.service;

import com.qweather.leframework.base.relationship.service.po.RelationshipEntity;
import com.qweather.leframework.model.Query;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public interface RelationshipQuery extends Query<RelationshipQuery, RelationshipEntity> {

    RelationshipQuery sourceId(Long sourceId);

    RelationshipQuery type(RelationshipType type);

    RelationshipQuery targetId(Long targetId);

    RelationshipQuery orderBySourceId(Query.Direction direction);

    RelationshipQuery orderByName(Direction direction);

    RelationshipQuery orderByTargetId(Direction direction);

}
