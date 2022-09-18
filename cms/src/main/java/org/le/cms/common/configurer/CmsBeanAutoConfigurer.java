package org.le.cms.common.configurer;

import org.le.cms.common.interfaces.VerifyCodeGenerator;
import org.le.cms.common.interfaces.defaults.CmsVerifyCodeGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * CMS mvc config
 * <p>
 * Created at 2018-09-27 13:53:27
 *
 * @author xiaole
 */
@org.springframework.context.annotation.Configuration
@AutoConfigureOrder(2)
public class CmsBeanAutoConfigurer {

    @Bean
    @ConditionalOnMissingBean(value = VerifyCodeGenerator.class)
    public VerifyCodeGenerator verifyCodeGenerator() {
        return new CmsVerifyCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(value = PathMatcher.class)
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

}
