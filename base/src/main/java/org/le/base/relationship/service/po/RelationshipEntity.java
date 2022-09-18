package org.le.base.relationship.service.po;

import java.io.Serializable;

/**
 * Created at 2018-08-06 09:11:22
 *
 * @author xiaole
 */
public class RelationshipEntity implements Serializable {

    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 分类
     */
    private String type;
    /**
     * 目标ID
     */
    private Long targetId;

    public Long getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTargetId() {
        return this.targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

}