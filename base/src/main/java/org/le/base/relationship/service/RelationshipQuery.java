package org.le.base.relationship.service;

import org.le.base.relationship.service.po.RelationshipEntity;
import org.le.model.Query;

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
