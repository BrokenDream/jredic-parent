package com.jredic.command.sub;

/**
 * The sub commands of 'OBJECT'
 * <p>
 * see {@link com.jredic.command.KeyCommand#OBJECT}
 *
 * @author David.W
 */
public enum ObjectSubCmd {

    /**
     * to get the number of references of the value associated with the specified key.
     */
    REFCOUNT,

    /**
     * to get the kind of internal representation used in order to store the value associated with a key.
     */
    ENCODING,

    /**
     * to get the number of seconds since the object stored at the specified key is idle (not requested by read or write operations).
     * While the value is returned in seconds the actual resolution of this timer is 10 seconds,
     * but may vary in future implementations.
     * This subcommand is available when maxmemory-policy is set to an LRU policy or noeviction.
     */
    IDLETIME,

    /**
     * to get the logarithmic access frequency counter of the object stored at the specified key.
     * This subcommand is available when maxmemory-policy is set to an LFU policy.
     */
    FREQ,

    /**
     * to get a succint help text.
     */
    HELP,
    ;

}
