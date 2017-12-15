package com.jredic.network.protocol;

/**
 * Constants of RESP.
 *
 * @author David.W
 */
class RESPConstants {

    static final int DATA_TYPE_LENGTH = 1;

    static final int CRLF_LENGTH = 2;

    static final short CRLF = CodecUtils.bytesToShort((byte)'\r',(byte)'\n');

    static final short NULL_DATA_LENGTH = CodecUtils.bytesToShort((byte)'-',(byte)'1');

    static final byte EMPTY_DATA_LENGTH = (byte)'0';

    static final int POSITIVE_LONG_MAX_LENGTH = 19; // length of Long.MAX_VALUE

    static final int REDIS_DATA_MAX_INLINE_LENGTH = 1024 * 64;

    //such as '+\r\n'、'-\r\n'
    static final int REDIS_DATA_STRING_MIN_LENGTH = 2;

    //such as ':0\r\n'
    static final int REDIS_DATA_INTEGER_MIN_LENGTH = 3;

    //such as '$-1\r\n'
    static final int REDIS_DATA_BULK_STRING_MIN_LENGTH = 4;

    //such as '*0\r\n'
    static final int REDIS_DATA_ARRAY_MIN_LENGTH = 3;

    private RESPConstants(){}

}
