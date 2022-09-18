package com.qweather.leframework.base.relationship.service.impl;

import com.qweather.leframework.base.relationship.service.cmd.ListRelationshipCmd;
import com.qweather.leframework.base.relationship.service.RelationshipQuery;
import com.qweather.leframework.base.relationship.service.RelationshipType;
import com.qweather.leframework.base.relationship.service.cmd.CountRelationshipCmd;
import com.qweather.leframework.base.relationship.service.po.RelationshipEntity;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class RelationshipQueryImpl extends AbstractQuery<RelationshipQuery, RelationshipEntity> implements RelationshipQuery {

    private Long sourceId;
    private String type;
    private Long targetId;
    private CommandExecutor commandExecutor;

    public RelationshipQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {

        ID("A.ID"), SOURCE_ID("A.SOURCE_ID"), TYPE("A.TYPE"), TARGET_ID("A.TARGET_ID");
        private String name;

        Query(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public RelationshipQuery sourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    @Override
    public RelationshipQuery type(RelationshipType type) {
        this.type = type.getType();
        return this;
    }

    @Override
    public RelationshipQuery targetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    @Override
    public RelationshipQuery orderBySourceId(Direction direction) {
        return orderBy(Query.SOURCE_ID, direction);
    }

    @Override
    public RelationshipQuery orderByName(Direction direction) {
        return orderBy(Query.TYPE, direction);
    }

    @Override
    public RelationshipQuery orderByTargetId(Direction direction) {
        return orderBy(Query.TARGET_ID, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountRelationshipCmd(this));
    }

    @Override
    protected List<RelationshipEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListRelationshipCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " A.SOURCE_ID ";
    }
}
