package com.qweather.leframework.base.rbac.user_extend.service.po;

import com.qweather.leframework.base.rbac.user_extend.service.UserExtend;

import java.io.Serializable;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public class UserExtendEntity implements Serializable {

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 角色ID
     */
    private Integer k;
    /**
     * 内容
     */
    private String v;

    public UserExtendEntity() {
        super();
    }

    public UserExtendEntity(Long uid, Integer k, String v) {
        super();
        this.uid = uid;
        this.k = k;
        this.v = v;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getK() {
        return this.k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public void setK(UserExtend k) {
        this.k = k.getK();
    }

    public String getV() {
        return this.v;
    }

    public void setV(String v) {
        this.v = v;
    }

}