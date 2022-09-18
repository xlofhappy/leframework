package org.le.base.common.configurer;

import org.le.base.common.i18n.LeMessageSource;
import org.le.base.common.interceptor.LeAdminPathInterceptor;
import org.le.base.common.interceptor.LeAuthInterceptor;
import org.le.base.common.util.BaseConstant;
import org.le.base.i18n.service.I18nService;
import org.le.base.rbac.permission.service.PermissionService;
import org.le.core.properties.LeProperties;
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

    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties messageSourceProperties() {
        return new MessageSourceProperties();
    }

    @Bean
    public MessageSource messageSource(I18nService i18nService, MessageSourceProperties properties) {
        return new LeMessageSource(i18nService, properties);
    }
}
