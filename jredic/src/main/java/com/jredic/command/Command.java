package com.jredic.command;

/**
 * The interface of redis commands.
 * See <a href="https://redis.io/commands">Redis Commands</a>.
 *
 * @author David.W
 */
public interface Command {

    /**
     * Get the values of command.
     * such as 'APPEND' and so on.
     *
     * @return
     *      the string command.
     */
    String[] values();

    /**
     * The Start Version is the Begin Version contains current command.
     *
     * @return
     *     the start version.
     */
    String startVersion();

}
