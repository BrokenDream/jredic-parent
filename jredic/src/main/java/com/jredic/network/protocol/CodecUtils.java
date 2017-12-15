package com.jredic.network.protocol;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

/**
 * Some codec utility methods.
 *
 * @author David.W
 */
class CodecUtils {

    static short bytesToShort(byte first, byte second) {
        return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ?
                (short) ((second << 8) | first) : (short) ((first << 8) | second);
    }

    static byte[] longToAsciiBytes(long value) {
        return Long.toString(value).getBytes(CharsetUtil.US_ASCII);
    }

    static byte[] intToAsciiBytes(int value) {
        return Integer.toString(value).getBytes(CharsetUtil.US_ASCII);
    }

}
