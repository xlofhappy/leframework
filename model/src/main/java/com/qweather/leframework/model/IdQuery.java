package com.qweather.leframework.model;

import java.io.Serializable;

/**
 * query for entity with id property
 *
 * @param <T>
 * @param <P>
 *
 * @author xiaole
 */
public interface IdQuery<T extends IdQuery<?, ?>, P extends Serializable> extends Query<T, P> {

    /**
     * query via id
     *
     * @param id identify
     *
     * @return Entity
     */
    T id(Long id);

    T orderById(Direction direction);
}
