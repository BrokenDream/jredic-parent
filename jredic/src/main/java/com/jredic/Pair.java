package com.jredic;

/**
 * Tuple class for holding key and value.
 *
 * @author David.W
 */
public class Pair {

    private String key;

    private String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
