package org.le.model.common.configurer;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationPropertiesScan("org.le.model.common.properties")
public class ModelAutoConfigurer  {

}
