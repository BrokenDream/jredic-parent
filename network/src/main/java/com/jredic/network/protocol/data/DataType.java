package com.jredic.network.protocol.data;

import com.jredic.network.RESPException;

/**
 * Data type defined in the <a href="https://redis.io/topics/protocol#resp-protocol-description">RESP</a>.
 *
 * @author David.W
 */
public enum DataType {

    SIMPLE_STRINGS((byte)'+'),
    ERRORS((byte)'-'),
    INTEGERS((byte)':'),
    BULK_STRINGS((byte)'$'),
    ARRAYS((byte)'*');

    private byte value;

    DataType(byte value){
        this.value = value;
    }

    public byte value() {
        return value;
    }

    public static DataType valueOf(byte value) {
        switch (value) {
            case '+':
                return SIMPLE_STRINGS;
            case '-':
                return ERRORS;
            case ':':
                return INTEGERS;
            case '$':
                return BULK_STRINGS;
            case '*':
                return ARRAYS;
            default:
                throw new RESPException("Unknown Redis DataType: " + value);
        }
    }

}
