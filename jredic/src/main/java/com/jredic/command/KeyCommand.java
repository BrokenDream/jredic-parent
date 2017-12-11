package com.jredic.command;

/**
 * The interface of redis KEY commands.
 * See <a href="https://redis.io/commands#key">Redis Keys Commands</a>.
 *
 * @author David.W
 */
public enum KeyCommand implements Command {

    DEL,
    DUMP,
    EXISTS,
    EXPIRE,
    EXPIREAT,
    PEXPIRE,
    PEXPIREAT,
    KEYS,
    MOVE,
    PERSIST,
    TTL,
    PTTL,
    RANDOMKEY,
    RENAME,
    RENAMENX,
    TYPE
    ;

    @Override
    public String getCommand() {
        return name();
    }

}
