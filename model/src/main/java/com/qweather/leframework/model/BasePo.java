package com.qweather.leframework.model;

import java.io.Serializable;

/**
 * BasePo only ID
 *
 * @author xiaole
 * @date 2018-07-25
 */
public class BasePo implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;

    public BasePo() {
    }

    public BasePo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( obj instanceof BasePo ) {
            Long s = ((BasePo) obj).getId();
            if ( this.id == null || s == null ) {
                return obj == this;
            } else {
                return this.id.equals(s);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
