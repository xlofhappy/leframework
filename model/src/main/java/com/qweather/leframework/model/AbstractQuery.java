package com.qweather.leframework.model;


import com.qweather.leframework.core.result.Page;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * Created at 2018-08-05 15:49:41
 *
 * @param <T>
 * @param <U>
 *
 * @author xiaole
 */
public abstract class AbstractQuery<T extends Query<?, ?>, U extends Serializable> extends ListQuery implements Query<T, U> {

    private static final long serialVersionUID = 1L;

    private enum ResultType {
        LIST, LIST_PAGE, SINGLE_RESULT, COUNT
    }

    private String orderByString;


    protected OrderBy orderProperty;

    protected enum NullHandlingOnOrder {
        /**
         * null value first in results
         */
        NULLS_FIRST, NULLS_LAST
    }

    private NullHandlingOnOrder nullHandlingOnOrder;


    /**
     * @param property {@link OrderBy}
     *
     * @return T
     */
    protected T orderBy(OrderBy property, Direction direction) {
        this.orderProperty = property;
        return direction(direction);
    }

    protected T orderBy(OrderBy property, Direction direction, NullHandlingOnOrder nullHandlingOnOrder) {
        this.nullHandlingOnOrder = nullHandlingOnOrder;
        return orderBy(property, direction);
    }

    private T direction(Direction direction) {
        if ( orderProperty == null ) {
            throw new IllegalArgumentException("You should call any of the orderBy methods first before specifying a direction");
        }
        addOrder(orderProperty.getName(), direction.getName(), nullHandlingOnOrder);
        orderProperty = null;
        nullHandlingOnOrder = null;
        return (T) this;
    }

    protected void checkQueryOk() {
        if ( orderProperty != null ) {
            throw new IllegalArgumentException("Invalid query: call asc() or desc() after using orderByXX()");
        }
    }

    @Override
    public @Nullable U one() {
        return (U) execute(ResultType.SINGLE_RESULT);
    }

    @Override
    public @Nullable U first() {
        this.skip = 0;
        this.limit = 1;
        List<U> list = executeList(null);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public @Nonnull List<U> list() {
        return (List<U>) execute(ResultType.LIST);
    }

    @Override
    public List<U> listPage(int skip, int limit) {
        this.skip = skip;
        this.limit = limit;
        return executeList(new Page(skip, limit));
    }

    @Override
    public List<U> listPage(Page page) {
        return executeList(page);
    }

    @Override
    public long count() {
        return (Long) execute(ResultType.COUNT);
    }

    private Object execute(ResultType resultType) {
        if ( resultType == ResultType.LIST ) {
            return executeList(null);
        } else if ( resultType == ResultType.SINGLE_RESULT ) {
            return executeSingleResult();
        } else if ( resultType == ResultType.LIST_PAGE ) {
            return executeList(null);
        } else {
            return executeCount();
        }
    }

    /**
     * execute count
     *
     * @return long
     */
    protected abstract long executeCount();

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param page used if the results must be paged. If null, no paging will be applied.
     *
     * @return list
     */
    protected abstract List<U> executeList(Page page);

    /**
     * single result
     *
     * @return U
     */
    private U executeSingleResult() {
        List<U> results = executeList(null);
        if ( results.size() == 1 ) {
            return results.get(0);
        } else if ( results.size() > 1 ) {
            throw new RuntimeException("Query return " + results.size() + " results instead of max 1");
        }
        return null;
    }

    private void addOrder(String column, String sortOrder, NullHandlingOnOrder nullHandlingOnOrder) {
        if ( orderByString == null ) {
            orderByString = "";
        } else {
            orderByString = orderByString + ", ";
        }

        String defaultOrderByClause = column + " " + sortOrder;

        if ( nullHandlingOnOrder != null ) {
            if ( nullHandlingOnOrder.equals(NullHandlingOnOrder.NULLS_FIRST) ) {
                orderByString = orderByString + "isnull(" + column + ") desc," + defaultOrderByClause;
            } else if ( nullHandlingOnOrder.equals(NullHandlingOnOrder.NULLS_LAST) ) {
                orderByString = orderByString + "isnull(" + column + ") asc," + defaultOrderByClause;
            }
        } else {
            orderByString = orderByString + defaultOrderByClause;
        }

    }

    public String getOrderByColumns() {
        if ( orderByString == null ) {
            return "A.ID ASC";
        } else {
            return orderByString;
        }
    }

}
