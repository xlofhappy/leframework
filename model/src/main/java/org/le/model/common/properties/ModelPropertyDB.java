package org.le.model.common.properties;

/**
 * @author xiaole
 */
public class ModelPropertyDB {

    private String baseTablePrefix = "le_";

    public String getBaseTablePrefix() {
        return baseTablePrefix;
    }

    public void setBaseTablePrefix(String baseTablePrefix) {
        if ( baseTablePrefix == null || baseTablePrefix.length() <= 0 ) {
            throw new Error("base table prefix can't be empty");
        } else {
            this.baseTablePrefix = baseTablePrefix;
        }
    }
}
