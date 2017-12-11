package com.jredic.command;

/**
 * The interface of redis string commands.
 * See <a href="https://redis.io/commands#string">Redis String Commands</a>.
 *
 * @author David.W
 */
public enum StringCommand implements Command{

    APPEND("2.0.0"),
    BITCOUNT("2.6.0"),
    BITFIELD("3.2.0"),
    BITOP("2.6.0"),
    BITPOS("2.8.7"),
    DECR("1.0.0"),
    DECRBY("1.0.0"),
    GET("1.0.0"),
    GETBIT("2.2.0"),
    GETRANGE("2.4.0"),
    GETSET("1.0.0"),
    INCR("1.0.0"),
    INCRBY("1.0.0"),
    INCRBYFLOAT("2.6.0"),
    MGET("1.0.0"),
    MSET("1.0.1"),
    MSETNX("1.0.1"),
    PSETEX("2.6.0"),
    SET("1.0.0"),
    SETBIT("2.2.0"),
    SETEX("2.0.0"),
    SETNX("1.0.0"),
    SETRANGE("2.2.0"),
    STRLEN("2.2.0"),
    ;

    private int svv;

    StringCommand(String sinceVersion) {
        this.svv = Commands.getValueFromSinceVersion(sinceVersion);
    }

    @Override
    public int getSinceVersionValue() {
        return svv;
    }

    @Override
    public String getCommand() {
        return name();
    }

}
