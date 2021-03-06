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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleStringsData that = (SimpleStringsData) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}
