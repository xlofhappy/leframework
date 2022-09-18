package org.le.base.relationship.service;

import org.le.base.relationship.service.po.RelationshipEntity;

import javax.annotation.Nonnull;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public interface RelationshipService {

    RelationshipQuery createRelationshipQuery();

    void addRelationship(RelationshipEntity entity);

    void deleteRelationship(Long sourceId, @Nonnull RelationshipType type, Long targetId);

    void deleteRelationshipBySourceId(Long sourceId, @Nonnull RelationshipType type);

}
