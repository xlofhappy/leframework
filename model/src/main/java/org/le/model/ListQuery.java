package org.le.model;

/**
 * for page query
 *
 * @author xiaole
 */
public class ListQuery {

    protected int limit = Integer.MAX_VALUE;
    protected int skip  = 0;

    public ListQuery() {
    }

    public ListQuery(int skip, int limit) {
        this.skip = skip;
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public int getFirstRow() {
        return skip + 1;
    }

    public int getLastRow() {
        if ( limit == Integer.MAX_VALUE ) {
            return limit;
        }
        return skip + limit + 1;
    }

    public int getLimit() {
        return limit;
    }


    public void setSkip(int skip) {
        this.skip = skip;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
