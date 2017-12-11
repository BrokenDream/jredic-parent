package com.jredic.command;

/**
 * The interface of redis strings commands.
 * See <a href="https://redis.io/commands#string">Redis Strings Commands</a>.
 *
 * @author David.W
 */
public enum StringsCommand implements Command{

    APPEND("APPEND"),
    BITCOUNT("BITCOUNT"),
    BITOP("BITOP"),
    DECR("DECR"),
    DECRBY("DECRBY"),
    GET("GET"),
    GETBIT("GETBIT"),
    GETRANGE("GETRANGE"),
    GETSET("GETSET"),
    INCR("INCR"),
    INCRBY("INCRBY"),
    INCRBYFLOAT("INCRBYFLOAT"),
    MGET("MGET"),
    MSET("MSET"),
    MSETNX("INCR"),
    PSETEX("INCR"),
    SET("INCR"),
    SETBIT("INCR"),
    SETEX("INCR"),
    SETNX("INCR"),
    SETRANGE("INCR"),
    STRLEN("STRLEN"),
    ;

    private String command;

    StringsCommand(String command){
        this.command = command;
    }

    @Override
    public String getCommand() {
        return command;
    }

}
