package com.qweather.leframework.base.relationship.service.impl;

import com.qweather.leframework.base.relationship.service.cmd.DeleteRelationshipBySourceIdCmd;
import com.qweather.leframework.base.relationship.service.cmd.DeleteRelationshipCmd;
import com.qweather.leframework.base.relationship.service.cmd.InsertRelationshipCmd;
import com.qweather.leframework.base.relationship.service.RelationshipQuery;
import com.qweather.leframework.base.relationship.service.RelationshipService;
import com.qweather.leframework.base.relationship.service.RelationshipType;
import com.qweather.leframework.base.relationship.service.po.RelationshipEntity;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    private final CommandExecutor executor;

    @Autowired
    public RelationshipServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public RelationshipQuery createRelationshipQuery() {
        return new RelationshipQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRelationship(RelationshipEntity entity) {
        executor.execute(new InsertRelationshipCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRelationship(Long sourceId, @Nonnull RelationshipType type, Long targetId) {
        executor.execute(new DeleteRelationshipCmd(sourceId, type, targetId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRelationshipBySourceId(Long sourceId, @Nonnull RelationshipType type) {
        executor.execute(new DeleteRelationshipBySourceIdCmd(sourceId, type));
    }

}
