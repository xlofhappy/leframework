package org.le.base.common.exception;

/**
 * Base exception
 *
 * @author xiaole
 */
public class LeException extends RuntimeException {

    public LeException(String message) {
        super(message);
    }

    public LeException(String message, Throwable e) {
        super(message, e);
    }
}
