package org.le.core.result;

import java.io.Serializable;

/**
 * return json message
 *
 * @author xiaole
 */
public class LeResult<T> implements Serializable {

    private boolean success;
    private String  code;
    private String  msg;
    private T       data;
    private Page    page;

    protected LeResult() {
    }

    private LeResult(String code) {
        this.code = code;
    }

    private LeResult(String code, T data) {
        this.code = code;
        this.data = data;
    }

    private LeResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> LeResult<T> success(String code, T data) {
        return new LeResult<>(code, data).setSuccess(true);
    }

    public static <T> LeResult<T> success(String code) {
        return (LeResult<T>) new LeResult<>(code).setSuccess(true);
    }

    public static <T> LeResult<T> error(String code) {
        return (LeResult<T>) new LeResult<>(code).setSuccess(false);
    }

    public static <T> LeResult<T> error(String code, String msg) {
        return (LeResult<T>) new LeResult<>(code, msg).setSuccess(false);
    }

    public boolean getSuccess() {
        return success;
    }

    public LeResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LeResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public LeResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public LeResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Page getPage() {
        return page;
    }

    public LeResult<T> setPage(Page page) {
        this.page = page;
        return this;
    }
}
