package com.qweather.leframework.cms.common.configurer;

import com.qweather.leframework.base.common.interceptor.LeAdminPathInterceptor;
import com.qweather.leframework.base.common.interceptor.LeAuthInterceptor;
import com.qweather.leframework.core.properties.LeProperties;
import com.qweather.leframework.core.util.SpringBeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;

/**
 * CMS mvc config
 * <p>
 * Created at 2018-09-27 13:53:27
 *
 * @author xiaole
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
public class CmsMvcAutoConfigurer implements WebMvcConfigurer, ApplicationContextAware {

    private final LeProperties leProperties;
    private final LeAuthInterceptor leAuthInterceptor;
    private final ResourceProperties resourceProperties;
    private final LeAdminPathInterceptor leAdminPathInterceptor;

    @Autowired
    public CmsMvcAutoConfigurer(LeProperties leProperties, LeAuthInterceptor leAuthInterceptor,
                                ResourceProperties resourceProperties, LeAdminPathInterceptor leAdminPathInterceptor) {
        this.leProperties = leProperties;
        this.leAuthInterceptor = leAuthInterceptor;
        this.leAdminPathInterceptor = leAdminPathInterceptor;
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration auth = registry.addInterceptor(leAuthInterceptor).addPathPatterns("/**");
        InterceptorRegistration adminPath = registry.addInterceptor(leAdminPathInterceptor).addPathPatterns("/admin/**");

        //exclude spring's resource path
        if (resourceProperties.getStaticLocations() != null) {
            auth.excludePathPatterns(resourceProperties.getStaticLocations());
            adminPath.excludePathPatterns(resourceProperties.getStaticLocations());
        }
        if (leProperties.getPath() != null) {
            //no free for admin path
            if (leProperties.getPath().getFree() != null) {
                auth.excludePathPatterns(leProperties.getPath().getFree());
                adminPath.excludePathPatterns(leProperties.getPath().getFree());
            }
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.setApplicationContext(applicationContext);
    }

}
