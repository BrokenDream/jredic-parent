package com.jredic.network.protocol.data;

/**
 * See <a href="https://redis.io/topics/protocol#resp-simple-strings">RESP#resp-simple-strings</a>.
 *
 * @author David.W
 */
public class SimpleStringsData implements Data {

    private String content;

    public SimpleStringsData(String content) {
        this.content = content;
    }

    @Override
    public DataType getType() {
        return DataType.SIMPLE_STRINGS;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "SimpleStrings[" + content + "]";
    }

}
