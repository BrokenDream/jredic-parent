package com.jredic;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * Some utility method for test.
 *
 * @author David.W
 */
public class TestUtils {

    public static String toString(ByteBuf byteBuf){
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return new String(bytes, Charset.forName("utf-8"));
    }

    public static long getUnixTime(){
        return System.currentTimeMillis() / 1000;
    }

}
