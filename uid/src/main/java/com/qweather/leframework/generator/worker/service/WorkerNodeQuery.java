package com.qweather.leframework.generator.worker.service;

import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeEntity;
import com.qweather.leframework.model.IdQuery;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface WorkerNodeQuery extends IdQuery<WorkerNodeQuery, WorkerNodeEntity> {

    WorkerNodeQuery hostAndPort(String host, String port);

}
