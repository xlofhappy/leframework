package org.le.uid.generator.common.configurer;

import org.le.core.interfaces.IdGenerator;
import org.le.uid.generator.common.impl.DefaultUidGenerator;
import org.le.uid.generator.properties.UidGeneratorProperties;
import org.le.uid.generator.worker.RecycleWorkerIdAssigner;
import org.le.uid.generator.worker.WorkerIdAssigner;
import org.le.uid.generator.worker.service.WorkerNodeService;
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
