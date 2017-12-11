package com.jredic.command;

/**
 * The interface of redis KEY commands.
 * See <a href="https://redis.io/commands#key">Redis Keys Commands</a>.
 *
 * @author David.W
 */
public enum KeyCommand implements Command {

    DEL("1.0.0"),
    DUMP("2.6.0"),
    EXISTS("1.0.0"),
    EXPIRE("1.0.0"),
    EXPIREAT("1.2.0"),
    KEYS("1.0.0"),
    MIGRATE("2.6.0"),
    MOVE("1.0.0"),
    OBJECT("2.2.3"),
    PERSIST("2.2.0"),
    PEXPIRE("2.6.0"),
    PEXPIREAT("2.6.0"),
    PTTL("2.6.0"),
    RANDOMKEY("1.0.0"),
    RENAME("1.0.0"),
    RENAMENX("1.0.0"),
    RESTORE("2.6.0"),
    SORT("1.0.0"),
    TOUCH("3.2.1"),
    TTL("1.0.0"),
    TYPE("1.0.0"),
    UNLINK("4.0.0"),
    WAIT("3.0.0"),
    SCAN("2.8.0"),
    ;

    private int svv;

    KeyCommand(String sinceVersion) {
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
