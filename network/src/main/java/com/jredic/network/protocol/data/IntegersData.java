package com.jredic.network.protocol.data;

/**
 * See <a href="https://redis.io/topics/protocol#resp-integers">RESP#resp-integers</a>.
 *
 * @author David.W
 */
public class IntegersData implements Data {

    private long value;

    public IntegersData(long value) {
        this.value = value;
    }

    @Override
    public DataType getType() {
        return DataType.INTEGERS;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IntegersData[" + value + "]";
    }
}
