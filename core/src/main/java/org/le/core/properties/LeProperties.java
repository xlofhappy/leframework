package org.le.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.ZoneOffset;

/**
 * cms properties configuration
 * <p>
 * created at 2018-09-27 14:22:04
 *
 * @author xiaole
 */
@ConfigurationProperties(prefix = LeProperties.PREFIX)
public class LeProperties {

    public static final String PREFIX = "le";

    private String workspace = "/tmp";

    private ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

    @NestedConfigurationProperty
    private LePropertyPath path;

    @NestedConfigurationProperty
    private LePropertyRedis redis;

    @NestedConfigurationProperty
    private LePropertyHttp http;

    public LeProperties() {
        this.path = new LePropertyPath();
        this.redis = new LePropertyRedis();
        this.http = new LePropertyHttp();
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public LePropertyPath getPath() {
        return path;
    }

    public void setPath(LePropertyPath path) {
        this.path = path;
    }

    public LePropertyRedis getRedis() {
        return redis;
    }

    public void setRedis(LePropertyRedis redis) {
        this.redis = redis;
    }

    public LePropertyHttp getHttp() {
        return http;
    }

    public void setHttp(LePropertyHttp http) {
        this.http = http;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public void setZoneOffset(float hour) {
        int h = (int) hour;
        int m = (int) ((hour - h) * 60);
        this.zoneOffset = ZoneOffset.ofHoursMinutes(h, m);
    }
}
