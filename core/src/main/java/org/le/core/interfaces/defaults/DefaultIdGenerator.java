package org.le.core.interfaces.defaults;


import org.le.core.interfaces.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Default Database id generator, uuid
 * Created at 2018-08-01 12:34:56
 *
 * @author xiaole
 */
public class DefaultIdGenerator implements IdGenerator {

    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Long getNextId() {
        return counter.incrementAndGet();
    }
}
