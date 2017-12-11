package com.jredic.network.protocol.data;

/**
 * See <a href="https://redis.io/topics/protocol#resp-bulk-strings">RESP#resp-bulk-strings</a>.
 *
 * @author David.W
 */
public class BulkStringsData implements Data {

    private String content;

    public BulkStringsData(String content) {
        this.content = content;
    }

    public BulkStringsData(){
    }

    @Override
    public DataType getType() {
        return DataType.BULK_STRINGS;
    }

    public String getContent() {
        return content;
    }

    private static final BulkStringsData NULL_BULK_STRING = new BulkStringsData();

    public static BulkStringsData getNullBulkString(){
        return NULL_BULK_STRING;
    }

    public boolean isNullBulkString(){
        return this == NULL_BULK_STRING;
    }

    private static final BulkStringsData EMPTY_BULK_STRING = new BulkStringsData();

    public static BulkStringsData getEmptyBulkString(){
        return EMPTY_BULK_STRING;
    }

    public boolean isEmptyBulkString(){
        return this == EMPTY_BULK_STRING;
    }

    @Override
    public String toString() {
        return "BulkStringsData[" + content + "]";
    }
}
