package org.le.model;

import java.io.Serializable;

/**
 * query for entity with status、 display ...
 *
 * @param <T>
 * @param <P>
 *
 * @author xiaole
 */
public interface BaseQuery<T extends BaseQuery<?, ?>, P extends Serializable> extends IdQuery<T, P> {

    T authorId(Long authorId);

    T modifyerId(Long modifyerId);

    T status(Short status);

    T display(Boolean display);

    T orderByAuthorTime(Direction direction);

    T orderByModifyTime(Direction direction);
}
