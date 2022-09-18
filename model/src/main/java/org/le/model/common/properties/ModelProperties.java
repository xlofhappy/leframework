package org.le.model.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiaole
 */
@ConfigurationProperties(prefix = ModelProperties.PREFIX)
public class ModelProperties {

    public static final String PREFIX = "le.db";

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