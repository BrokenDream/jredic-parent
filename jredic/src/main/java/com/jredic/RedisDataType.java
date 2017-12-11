package com.jredic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author David.W
 */
public enum RedisDataType {

    NONE("none"),
    STRING("string"),
    LIST("list"),
    SET("set"),
    ZSET("zset"),
    HASH("hash"),
    ;

    private String type;

    RedisDataType(String type) {
        this.type = type;
    }

    private static final Map<String, RedisDataType> INNER_MAP = new HashMap<>();
    static{
        for(RedisDataType value : RedisDataType.values()){
            INNER_MAP.put(value.type, value);
        }
    }

    public static RedisDataType get(String type){
        return INNER_MAP.get(type);
    }

}
