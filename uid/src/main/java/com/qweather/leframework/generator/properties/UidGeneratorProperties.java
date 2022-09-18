package com.qweather.leframework.generator.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiaole
 */
@ConfigurationProperties(prefix = UidGeneratorProperties.PREFIX)
public class UidGeneratorProperties {

    public static final String PREFIX = "le.uid";

    private int timeBits = 41;
    private int workerBits = 10;
    private int seqBits = 12;
    private String epochStr = "2016-05-20";

    public int getTimeBits() {
        return timeBits;
    }

    public void setTimeBits(int timeBits) {
        this.timeBits = timeBits;
    }

    public int getWorkerBits() {
        return workerBits;
    }

    public void setWorkerBits(int workerBits) {
        this.workerBits = workerBits;
    }

    public int getSeqBits() {
        return seqBits;
    }

    public void setSeqBits(int seqBits) {
        this.seqBits = seqBits;
    }

    public String getEpochStr() {
        return epochStr;
    }

    public void setEpochStr(String epochStr) {
        this.epochStr = epochStr;
    }
}