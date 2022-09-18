package org.le.uid.generator.worker.service.pojo;

/**
 * Type of worker id
 *
 * @author xiaole
 * @date 2019-08-07 12:31:45
 */
public enum WorkerNodeType {
    CONTAINER(1), ACTUAL(2);

    /**
     * Lock type
     */
    private final Integer type;

    /**
     * Constructor with field of type
     */
    WorkerNodeType(Integer type) {
        this.type = type;
    }

    public Integer value() {
        return type;
    }
}