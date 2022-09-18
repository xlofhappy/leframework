package com.qweather.leframework.generator.common.configurer;

import com.qweather.leframework.core.interfaces.IdGenerator;
import com.qweather.leframework.generator.common.impl.DefaultUidGenerator;
import com.qweather.leframework.generator.properties.UidGeneratorProperties;
import com.qweather.leframework.generator.worker.RecycleWorkerIdAssigner;
import com.qweather.leframework.generator.worker.WorkerIdAssigner;
import com.qweather.leframework.generator.worker.service.WorkerNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * CMS mvc config
 * <p>
 * Created at 2019-08-08 14:53:27
 *
 * @author xiaole
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({WorkerIdAssigner.class})
@EnableConfigurationProperties({UidGeneratorProperties.class})
public class UidGeneratorAutoConfigurer {

    @Bean
    @ConditionalOnMissingBean(value = WorkerIdAssigner.class)
    public WorkerIdAssigner workerIdAssigner(WorkerNodeService workerNodeService) {
        return new RecycleWorkerIdAssigner(workerNodeService, uidGeneratorProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = IdGenerator.class)
    public IdGenerator idGenerator() {
        return new DefaultUidGenerator(workerIdAssigner, uidGeneratorProperties);
    }


    private final WorkerIdAssigner workerIdAssigner;
    private final UidGeneratorProperties uidGeneratorProperties;

    @Autowired
    public UidGeneratorAutoConfigurer(UidGeneratorProperties uidGeneratorProperties,
                                      WorkerIdAssigner workerIdAssigner) {
        this.uidGeneratorProperties = uidGeneratorProperties;
        this.workerIdAssigner = workerIdAssigner;
    }

}
