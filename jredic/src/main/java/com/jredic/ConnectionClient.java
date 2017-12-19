package com.jredic;

import com.jredic.exception.IllegalParameterException;
import com.jredic.exception.JredicException;

/**
 * Define operations for 'Connection' commands.
 * See {@link com.jredic.command.ConnectionCommand}
 *
 * @author David.W
 */
public interface ConnectionClient {

    /**
     * Request for authentication in a password-protected Redis server.
     * Redis can be instructed to require a password before allowing clients to execute commands.
     *
     * @param password the password for Redis server.
     * @throws IllegalParameterException if password is blank.
     * @throws JredicException if auth failed OR some other err occur.
     */
    void auth(String password);

    /**
     * Returns message.
     *
     * @param message message to send.
     * @return
     *      message.
     * @throws IllegalParameterException if message is blank.
     * @throws JredicException if some other err occur.
     */
    String echo(String message);

    /**
     * Returns 'PONG'.
     *
     * @return
     *      'PONG'
     */
    String ping();

    /**
     * Returns message. like {@link #echo(String)}
     *
     * @param message message to send.
     * @return
     *      message.
     * @throws IllegalParameterException if message is blank.
     * @throws JredicException if some other err occur.
     */
    String ping(String message);

    /**
     * Ask the server to close the connection.
     * @throws JredicException if some err occur.
     */
    void quit();

    /**
     * Select the Redis logical database having the specified zero-based numeric index.
     * New connections always use the database 0.
     *
     * @param index the index of redis db to select.
     * @throws IllegalParameterException if index is negative.
     * @throws JredicException if some other err occur.
     */
    void select(int index);

}
