package com.qweather.leframework.model.common.configurer;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationPropertiesScan("com.qweather.leframework.model.common.properties")
public class ModelAutoConfigurer  {

}
