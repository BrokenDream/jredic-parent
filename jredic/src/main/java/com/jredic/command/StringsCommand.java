package com.jredic.command;

/**
 * The interface of redis strings commands.
 * See <a href="https://redis.io/commands#string">Redis Strings Commands</a>.
 *
 * @author David.W
 */
public enum StringsCommand implements Command{

    APPEND,
    BITCOUNT,
    BITOP,
    DECR,
    DECRBY,
    GET,
    GETBIT,
    GETRANGE,
    GETSET,
    INCR,
    INCRBY,
    INCRBYFLOAT,
    MGET,
    MSET,
    MSETNX,
    PSETEX,
    SET,
    SETBIT,
    SETEX,
    SETNX,
    SETRANGE,
    STRLEN,
    ;

    @Override
    public String getCommand() {
        return name();
    }

}
