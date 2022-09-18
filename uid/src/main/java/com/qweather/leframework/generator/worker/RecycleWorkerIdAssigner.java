/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qweather.leframework.generator.worker;

import com.qweather.leframework.core.thread.Notifier;
import com.qweather.leframework.core.util.Constant;
import com.qweather.leframework.generator.common.utils.DockerUtils;
import com.qweather.leframework.generator.common.utils.NetUtils;
import com.qweather.leframework.generator.properties.UidGeneratorProperties;
import com.qweather.leframework.generator.worker.service.WorkerNodeService;
import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeEntity;
import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an implementation of {@link WorkerIdAssigner},
 * the worker id will be discarded after assigned to the UidGenerator
 *
 * @author yutianbao
 */
@Service
public class RecycleWorkerIdAssigner implements WorkerIdAssigner {

    private WorkerNodeEntity currentWorkerNode = null;

    private final WorkerNodeService workerNodeService;
    private final UidGeneratorProperties uidGeneratorProperties;

    public RecycleWorkerIdAssigner(WorkerNodeService workerNodeService, UidGeneratorProperties uidGeneratorProperties) {
        this.workerNodeService = workerNodeService;
        this.uidGeneratorProperties = uidGeneratorProperties;
    }

    /**
     * Assign worker id base on database.<p>
     * If there is host name & port in the environment, we considered that the node runs in Docker container<br>
     * Otherwise, the node runs on an actual machine.
     *
     * @return assigned worker id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long assignWorkerId() {
        if (currentWorkerNode == null) {
            // build worker node entity
            WorkerNodeEntity workerNodeEntity = buildWorkerNode();

            long maxWorkerId = ~(-1L << uidGeneratorProperties.getWorkerBits());
            assert maxWorkerId > 0;

            long workerNodeId = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.TIME_FORMAT);
            do {
                ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
                currentWorkerNode = this.workerNodeService.createWorkerNodeQuery().id(workerNodeId).one();
                if (currentWorkerNode != null) {
                    ZonedDateTime dateTime = LocalDateTime.parse(currentWorkerNode.getModifyTime(), formatter).atZone(ZoneOffset.UTC);
                    if (dateTime.isAfter(now)) {
                        throw new Error(String.format("Local time error, db %s, machine %s, start failed", currentWorkerNode.getModifyTime(), now.format(formatter)));
                    } else if (dateTime.isBefore(now.minusMinutes(30))) {
                        this.workerNodeService.deleteWorkerNode(workerNodeId);
                    }
                    currentWorkerNode = null;
                } else {
                    workerNodeEntity.setId(workerNodeId);
                    workerNodeEntity.setAuthorTime(now.format(formatter));
                    workerNodeEntity.setModifyTime(now.format(formatter));
                    this.workerNodeService.addWorkerNode(workerNodeEntity);
                    currentWorkerNode = workerNodeEntity;
                }
                workerNodeId++;
                if (workerNodeId == maxWorkerId && currentWorkerNode == null) {
                    throw new Error("can't find a valid worker id");
                }
                workerNodeId = workerNodeId == maxWorkerId ? 0 : workerNodeId;
            } while (currentWorkerNode == null);

            // every second update time
            keepAlive();
        }
        return currentWorkerNode.getId();
    }

    private void keepAlive() {
        Notifier.one(1).startJob(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(5000);
                    this.workerNodeService.keepAliveWorkerNode(currentWorkerNode.getId(), ZonedDateTime.now(ZoneOffset.UTC).format(java.time.format.DateTimeFormatter.ofPattern(Constant.TIME_FORMAT)));
                } catch (Exception ignored) {
                }
            }
        });
    }

    /**
     * Build worker node entity by IP and PORT
     */
    private WorkerNodeEntity buildWorkerNode() {
        WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort());
        } else {
            workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
            workerNodeEntity.setHostName(NetUtils.getLocalAddress());
            workerNodeEntity.setPort("-");
        }
        return workerNodeEntity;
    }

}
