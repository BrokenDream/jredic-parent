package com.jredic.command;

/**
 * @author David.W
 */
public enum HashCommand implements Command {

    HDEL("2.0.0"),
    HEXISTS("2.0.0"),
    HGET("2.0.0"),
    HGETALL("2.0.0"),
    HINCRBY("2.0.0"),
    HINCRBYFLOAT("2.6.0"),
    HKEYS("2.0.0"),
    HLEN("2.0.0"),
    HMGET("2.0.0"),
    HMSET("2.0.0"),
    HSCAN("2.8.0"),
    HSET("2.0.0"),
    HSETNX("2.0.0"),
    HSTRLEN("3.2.0"),
    HVALS("2.0.0"),
    ;

    private int svv;

    HashCommand(String sinceVersion) {
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
