package com.qweather.leframework.core.interfaces;

/**
 * Created at 2018-08-01 12:34:56
 *
 * @author xiaole
 */
@FunctionalInterface
public interface IdGenerator {

    Long getNextId();
}
