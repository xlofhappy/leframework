package com.qweather.leframework.core.util;

import org.springframework.context.ApplicationContext;

/**
 * Spring bean util
 *
 * @author xiaole
 * @date 2018-10-30 19:28:43
 */
public class SpringBeanUtil {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringBeanUtil.applicationContext == null && applicationContext != null) {
            synchronized (SpringBeanUtil.class) {
                if (SpringBeanUtil.applicationContext == null && applicationContext != null) {
                    SpringBeanUtil.applicationContext = applicationContext;
                }
            }
        }
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

}
