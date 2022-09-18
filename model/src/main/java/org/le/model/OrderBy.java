package org.le.model;

import java.io.Serializable;

/**
 * Order by property's interface
 *
 * @author xiaole
 */
public interface OrderBy extends Serializable {

    /**
     * get the real properties's name in sql
     *
     * @return real properties's name in sql
     */
    String getName();
}
