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
     * <b>svv</b> is short for 'Start Version Value',
     * the 'Start Version' is the Begin Version contains current command.
     * the 'ssv' is a int value that represents the 'Start Version'.
     * if the 'Start Version' is '2.8.13', then the 'ssv' is 20813.
     * it's convenient to to comparing.
     *
     * @return
     *     the int value represents the 'Start Version'.
     */
    int svv();

}
