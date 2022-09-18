package org.le.core.configurer;

import org.le.core.factory.RedisInstanceFactory;
import org.le.core.interceptors.LeBaseInterceptor;
import org.le.core.properties.LeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

/**
 * CMS mvc config
 * <p>
 * Created at 2018-09-27 13:53:27
 *
 * @author xiaole
 */
@org.springframework.context.annotation.Configuration
@ConfigurationPropertiesScan(basePackageClasses = {LeProperties.class})
public class LeCoreConfigurer {

    @Bean
    @ConditionalOnClass({ServerProperties.class, LeProperties.class})
    public LeBaseInterceptor leBaseInterceptor(ServerProperties serverProperties, LeProperties leProperties) {
        return new LeBaseInterceptor(serverProperties, leProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "le.redis", name = "usable")
    @ConditionalOnClass({LeProperties.class})
    public RedisInstanceFactory redisInstanceFactory(LeProperties leProperties) {
        return new RedisInstanceFactory(leProperties.getRedis());
    }

}
