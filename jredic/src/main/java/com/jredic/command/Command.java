package com.jredic.command;

/**
 * The interface of redis commands.
 * See <a href="https://redis.io/commands">Redis Commands</a>.
 *
 * @author David.W
 */
public interface Command {

    /**
     * 获取命令。
     *
     * @return
     *      命令。
     */
    String getCommand();

    /**
     * 获取起始版本值。
     *
     * @return
     *     FIXME 描述
     */
    int getSinceVersionValue();

}
