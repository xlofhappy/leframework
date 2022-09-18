package org.le.core.result;

public class Page {

    /**
     * while searching the database, how many records to be skipped
     */
    private int skip = 0;
    /**
     * while searching the database, how many records wanted
     */
    private int limit = 15;
    /**
     * current page
     */
    private int current = 1;
    /**
     * record count each page
     * default 15
     */
    private int rows = limit;
    private final int maxRows = 200;
    /**
     * record count
     */
    private long count;
    /**
     * page count based rows
     */
    private int pageTotal;

    public Page(int skip, int limit) {
        if (skip >= 0 && limit > 0) {
            this.skip = skip;
            this.limit = limit;
            this.rows = limit;
        } else {
            throw new IllegalArgumentException(" skip must not less than 0 and limit must rather than 0 ");
        }
    }

    public Page() {
    }

    public int getSkip() {
        return skip == -1 ? (current - 1) * rows : skip;
    }

    public int getLimit() {
        return limit == -1 ? rows : limit;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current > 0) {
            this.current = current;
            this.skip = (current - 1) * rows;
        } else {
            throw new IllegalArgumentException(" current must rather than 0 ");
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows > 0) {
            rows = Math.min(rows, maxRows);
            this.rows = rows;
            this.skip = (this.current - 1) * rows;
            this.limit = rows;
            this.pageTotal = (int) (this.count / this.rows + 1);
        } else {
            throw new IllegalArgumentException(" rows must rather than 1 ");
        }
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        if (count >= 0) {
            this.count = count;
            int d = (int) (count * 1D / this.rows);
            this.pageTotal = count % this.rows == 0L ? d : ++d;
        } else {
            throw new IllegalArgumentException(" count must rather than 1 ");
        }
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public int getPrev() {
        return current > 1 ? current - 1 : current;
    }

    public int getNext() {
        return current < pageTotal ? current + 1 : current;
    }
}
