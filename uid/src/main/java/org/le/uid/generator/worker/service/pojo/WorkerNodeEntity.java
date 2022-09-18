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
package org.le.uid.generator.worker.service.pojo;

import org.le.model.po.LePo;

import java.util.Arrays;

/**
 * Entity for M_WORKER_NODE
 *
 * @author xiaole
 */
public class WorkerNodeEntity extends LePo {

    /**
     * Type of CONTAINER: HostName, ACTUAL : IP.
     */
    private String hostName;

    /**
     * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    private String port;

    /**
     * type of {@link WorkerNodeType}
     */
    private WorkerNodeType type;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getType() {
        return type.value();
    }

    public void setType(int type) {
        Arrays.stream(WorkerNodeType.values()).filter(p -> p.value() == type).findFirst().ifPresent(workerNodeType -> this.type = workerNodeType);
    }

    public void setType(WorkerNodeType type) {
        this.type = type;
    }

}
