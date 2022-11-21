package com.qweather.leframework.base.common.configurer;

import com.qweather.leframework.base.common.interceptor.LeAdminPathInterceptor;
import com.qweather.leframework.base.common.interceptor.LeAuthInterceptor;
import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.i18n.service.I18nService;
import com.qweather.leframework.base.common.i18n.LeMessageSource;
import com.qweather.leframework.base.rbac.permission.service.PermissionService;
import com.qweather.leframework.core.properties.LeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.util.PathMatcher;

/**
 * CMS mvc config
 * <p>
 * Created at 2018-09-27 13:53:27
 *
 * @author xiaole
 */
@org.springframework.context.annotation.Configuration
public class LeBaseConfigurer {

    @Bean
    @ConditionalOnMissingBean(value = PathMatcher.class)
    public PathMatcher pathMatcher() {
        return BaseConstant.PATH_MATCHER;
    }

    @Bean
    @ConditionalOnClass({PathMatcher.class, LeProperties.class})
    public LeAuthInterceptor leAuthInterceptor(LeProperties leProperties, PathMatcher pathMatcher) {
        return new LeAuthInterceptor(leProperties.getPath(), pathMatcher);
    }

    @Bean
    @ConditionalOnBean({PermissionService.class})
    public LeAdminPathInterceptor leAdminPathInterceptor(PermissionService permissionService) {
        return new LeAdminPathInterceptor(permissionService);
    }

    /**
     * 兼容Spring Message Auto Configuration
     * {@link org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration}
     *
     * @author xiaole
     * @date 2022-03-28T19:07:26+08:00
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties messageSourceProperties() {
        return new MessageSourceProperties();
    }

    /**
     * 自定义 messageSource
     * leMessageSource = Spring Message Source + Database i18n
     *
     * @author xiaole
     * @date 2022-03-28T19:18:08+08:00
     */
    @Bean
    public MessageSource messageSource(I18nService i18nService, MessageSourceProperties properties) {
        return new LeMessageSource(i18nService, properties);
    }
}
