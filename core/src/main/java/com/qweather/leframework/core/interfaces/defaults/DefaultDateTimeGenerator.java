package com.qweather.leframework.core.interfaces.defaults;


import com.qweather.leframework.core.interfaces.DateTimeGenerator;

import java.time.ZonedDateTime;

/**
 * Default time generator, only return the time based system
 *
 * @author xiaole
 */
public class DefaultDateTimeGenerator implements DateTimeGenerator {

    @Override
    public ZonedDateTime getCurrent() {
        return ZonedDateTime.now();
    }

}
