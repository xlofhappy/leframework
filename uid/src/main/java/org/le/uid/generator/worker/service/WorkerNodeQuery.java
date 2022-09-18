package org.le.uid.generator.worker.service;

import org.le.model.IdQuery;
import org.le.uid.generator.worker.service.pojo.WorkerNodeEntity;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface WorkerNodeQuery extends IdQuery<WorkerNodeQuery, WorkerNodeEntity> {

    WorkerNodeQuery hostAndPort(String host, String port);

}
