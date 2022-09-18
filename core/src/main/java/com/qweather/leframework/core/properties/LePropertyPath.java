package com.qweather.leframework.core.properties;

import java.util.List;

/**
 * common path for cms
 * <p>
 * Created at 2018-10-07 18:51:00
 *
 * @author xiaole
 */
public class LePropertyPath {

    public static final String UNAUTHORIZED = "/unauthorized";

    /**
     * path for no permission to access
     */
    private String unAuthorized = UNAUTHORIZED;
    /**
     * path without  auth check
     */
    private List<String> free;
    /**
     * path just login for  auth check
     */
    private List<String> login;
    /**
     * key for redirect, default: 'next'
     * eg:  next=http://xxxx.com  ,   the key is 'next'
     */
    private String redirectKey = "next";
    /**
     * error code if url check fails
     */
    private String errorCode;

    public String getUnAuthorized() {
        return unAuthorized;
    }

    public void setUnAuthorized(String unAuthorized) {
        this.unAuthorized = unAuthorized;
    }

    public List<String> getFree() {
        return free;
    }

    public void setFree(List<String> free) {
        this.free = free;
    }

    public List<String> getLogin() {
        return login;
    }

    public void setLogin(List<String> login) {
        this.login = login;
    }

    public String getRedirectKey() {
        return redirectKey;
    }

    public void setRedirectKey(String redirectKey) {
        this.redirectKey = redirectKey;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
