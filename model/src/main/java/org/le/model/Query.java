package org.le.model;


import org.le.core.result.Page;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Base query interface
 *
 * @param <T> subclass of Query
 * @param <P> actual entity class
 * @author xiaole
 */
public interface Query<T extends Query<?, ?>, P> {

    /**
     * sort direction
     */
    enum Direction {
        /**
         * asc
         */
        ASCENDING("asc"),
        /**
         * desc
         */
        DESCENDING("desc");
        String name;

        Direction(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * count
     *
     * @return count of matched records
     */
    long count();

    /**
     * get single record with conditions
     * When more than one records matched, got an exception
     *
     * @see #first
     * @return P
     */
    P one();

    /**
     * get first record with conditions
     * When more than one records matched, return the first one
     *
     * @return P
     */
    P first();

    /**
     * get matched records as ArrayList with conditions
     *
     * @return ArrayList&lt;P&gt;
     */
    @Nonnull List<P> list();


    /**
     * 执行查询，根据条件获取结果列表
     *
     * @param skip  跳过前多少条
     * @param limit 查出多少条
     * @return ArrayList&lt;P&gt;
     */
    List<P> listPage(int skip, int limit);

    List<P> listPage(Page page);

}
