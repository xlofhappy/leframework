package com.qweather.leframework.core.config;

import com.qweather.leframework.core.properties.LeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Base Config for cms
 *
 * @author xiaole
 * @date 2018-08-30 12:00:02
 */
@Component
public class Config {

    private static LeProperties leProperties;
    private static DaoConfig daoConfig;

    public static LeProperties getLeProperties() {
        return leProperties;
    }

    @Autowired
    public void setLeProperties(LeProperties leProperties) {
        Config.leProperties = leProperties;
    }

    public static DaoConfig getDaoConfig() {
        return Config.daoConfig;
    }

    @Autowired
    public void setDaoConfig(DaoConfig daoConfig) {
        Config.daoConfig = daoConfig;
    }

}
