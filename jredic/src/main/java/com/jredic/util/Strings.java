package com.jredic.util;

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

}
