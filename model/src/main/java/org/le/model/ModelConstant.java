package org.le.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Le Model Constant
 *
 * @author xiaole
 */
public class ModelConstant {

    public static final Map<String, String> DATABASE_LIMIT_BEFORE;

    static {
        Map<String, String> temp = new HashMap<>();
        temp.put("h2", "");
        temp.put("hsql", "");
        temp.put("mysql", "");
        temp.put("postgres", "");
        temp.put("postgresql", "");
        temp.put("oracle", "select * from ( select a.*, ROWNUM rnum from (");
        temp.put("db2", "SELECT SUB.* FROM (");
        temp.put("sqlserver", "SELECT SUB.* FROM (");
        DATABASE_LIMIT_BEFORE = Collections.unmodifiableMap(temp);
    }

    public static final Map<String, String> DATABASE_LIMIT_BETWEEN;

    static {
        Map<String, String> temp = new HashMap<>();
        temp.put("h2", "");
        temp.put("hsql", "");
        temp.put("mysql", "");
        temp.put("postgres", "");
        temp.put("postgresql", "");
        temp.put("oracle", "");
        temp.put("db2", ", row_number() over (ORDER BY ${orderByColumns}) rnk FROM ( select distinct RES.* ");
        temp.put("sqlserver", ", row_number() over (ORDER BY ${orderByColumns}) rnk FROM ( select distinct RES.* ");
        DATABASE_LIMIT_BETWEEN = Collections.unmodifiableMap(temp);
    }

    public static final Map<String, String> DATABASE_LIMIT_AFTER;

    static {
        Map<String, String> temp = new HashMap<>();
        temp.put("h2", " LIMIT #{limit} OFFSET #{skip} ");
        temp.put("hsql", " LIMIT #{limit} OFFSET #{skip} ");
        temp.put("mysql", " LIMIT #{limit} OFFSET #{skip} ");
        temp.put("postgres", " LIMIT #{limit} OFFSET #{skip} ");
        temp.put("postgresql", " LIMIT #{limit} OFFSET #{skip} ");
        temp.put("oracle", " ) a where ROWNUM < #{lastRow}) where rnum  >= #{firstRow} ");
        temp.put("db2", " )RES ) SUB WHERE SUB.rnk >= #{firstRow} AND SUB.rnk < #{lastRow} ");
        temp.put("sqlserver", " )RES ) SUB WHERE SUB.rnk >= #{firstRow} AND SUB.rnk < #{lastRow} ");
        DATABASE_LIMIT_AFTER = Collections.unmodifiableMap(temp);
    }

    public static final Map<String, String> DATABASE_ORDER_BY;

    static {
        Map<String, String> temp = new HashMap<>();
        temp.put("h2", " ORDER BY ${orderByColumns} ");
        temp.put("hsql", " ORDER BY ${orderByColumns} ");
        temp.put("mysql", " ORDER BY ${orderByColumns} ");
        temp.put("postgres", " ORDER BY ${orderByColumns} ");
        temp.put("postgresql", " ORDER BY ${orderByColumns} ");
        temp.put("oracle", " ORDER BY ${orderByColumns} ");
        temp.put("db2", " ORDER BY ${orderByColumns} ");
        temp.put("sqlserver", " ORDER BY ${orderByColumns} ");
        DATABASE_ORDER_BY = Collections.unmodifiableMap(temp);
    }
}
