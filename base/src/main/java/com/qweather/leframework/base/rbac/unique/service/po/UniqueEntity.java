package com.qweather.leframework.base.rbac.unique.service.po;

import com.qweather.leframework.base.rbac.unique.service.UniqueType;

import java.io.Serializable;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class UniqueEntity implements Serializable {

    /**
     * User ID
     */
    private Long uid;
    /**
     * Union id with type
     */
    private String unionId;
    /**
     * the type of the unique-id
     * Union id with type
     */
    private String type;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(UniqueType type) {
        this.type = type.type();
    }

}