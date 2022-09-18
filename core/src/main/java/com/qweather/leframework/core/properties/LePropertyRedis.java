package com.qweather.leframework.core.properties;

/**
 * redis properties for le
 * <p>
 * Created at 2019-05-28 11:24:00
 *
 * @author xiaole
 */
public class LePropertyRedis {

    private boolean usable = false;
    private String[] addr;
    private int[] port;
    private String[] auth;
    private int[] db;
    private int maxIdle = 8;
    private int maxActive = 30;
    private int minIdle = 4;
    /**
     * default: 3000
     * unit: second
     */
    private int maxWait = 3000;
    private int timeout = 3000;

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public String[] getAddr() {
        return addr;
    }

    public void setAddr(String[] addr) {
        this.addr = addr;
    }

    public int[] getPort() {
        return port;
    }

    public void setPort(int[] port) {
        this.port = port;
    }

    public String[] getAuth() {
        return auth;
    }

    public void setAuth(String[] auth) {
        this.auth = auth;
    }

    public int[] getDb() {
        return db;
    }

    public void setDb(int[] db) {
        this.db = db;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
