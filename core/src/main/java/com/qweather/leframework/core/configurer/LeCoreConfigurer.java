package com.qweather.leframework.core.configurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qweather.leframework.core.factory.RedisInstanceFactory;
import com.qweather.leframework.core.interceptors.LeBaseInterceptor;
import com.qweather.leframework.core.properties.LeProperties;
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
    @ConditionalOnProperty(prefix = "le.http", name = "session-id")
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
