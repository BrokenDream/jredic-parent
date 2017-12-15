package com.jredic.network.protocol.data;

import com.jredic.util.Strings;

import java.nio.charset.Charset;

/**
 * See <a href="https://redis.io/topics/protocol#resp-bulk-strings">RESP#resp-bulk-strings</a>.
 *
 * @author David.W
 */
public class BulkStringsData implements Data {

    /*
     * we use 'content' to keep the real data.
     * and use 'stringContent' for convenient.
     */

    private byte[] content;

    private volatile String stringContent;

    public BulkStringsData(String stringContent) {
        this.stringContent = stringContent;
        this.content = Strings.decodeByUTF8(stringContent);
    }

    public BulkStringsData(byte[] content) {
        this.content = content;
        this.stringContent = new String(content, Charset.forName("utf-8"));
    }

    public BulkStringsData(){
    }

    @Override
    public DataType getType() {
        return DataType.BULK_STRINGS;
    }

    public byte[] getContent() {
        return content;
    }

    public String getStringContent(){
        return stringContent;
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
        return "BulkStringsData[" + stringContent + "]";
    }
}
