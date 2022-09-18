package org.le.uid.generator.worker.service;

import org.le.uid.generator.worker.service.pojo.WorkerNodeEntity;

/**
 * Created at 2019-08-07 14:34:13
 *
 * @author xiaole
 */
public interface WorkerNodeService {

    WorkerNodeQuery createWorkerNodeQuery();

    void addWorkerNode(WorkerNodeEntity entity);

    void deleteWorkerNode(Long id);

    void keepAliveWorkerNode(Long id, String time);
}
