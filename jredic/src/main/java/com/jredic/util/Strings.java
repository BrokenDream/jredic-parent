package com.jredic.util;

import java.nio.charset.Charset;

/**
 * Utility methods for String.
 *
 * @author David.W
 */
public class Strings {

    /**
     * test if the given string is null or is the empty string.
     *
     * @param s a string to check.
     * @return
     *      return true if the given string is null or is the empty string; otherwise false.
     */
    public static boolean isNullOrEmpty(String s){
        return s == null || s.isEmpty();
    }

    /**
     * decode a String to byte array by UTF-8 charset.
     *
     * @param s a string to decode.
     * @return
     *      a byte array from s.
     */
    public static byte[] decodeByUTF8(String s){
        return s.getBytes(Charset.forName("utf-8"));
    }

    private Strings(){}

}
