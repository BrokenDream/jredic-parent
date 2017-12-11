package com.jredic.command;

/**
 * @author David.W
 */
public enum ListCommand implements Command {

    BLPOP("2.0.0"),
    BRPOP("2.0.0"),
    BRPOPLPUSH("2.2.0"),
    LINDEX("1.0.0"),
    LINSERT("2.2.0"),
    LLEN("1.0.0"),
    LPOP("1.0.0"),
    LPUSH("1.0.0"),
    LPUSHX("2.2.0"),
    LRANGE("1.0.0"),
    LREM("1.0.0"),
    LSET("1.0.0"),
    LTRIM("1.0.0"),
    RPOP("1.0.0"),
    RPOPLPUSH("1.2.0"),
    RPUSH("1.0.0"),
    RPUSHX("2.2.0"),
    ;

    private int svv;

    ListCommand(String sinceVersion) {
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
