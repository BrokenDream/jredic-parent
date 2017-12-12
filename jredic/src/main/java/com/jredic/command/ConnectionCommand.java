package com.jredic.command;

/**
 * The interface of redis <b>connection</b> commands.
 * See <a href="https://redis.io/commands#connection">Redis Connection Commands</a>.
 *
 * @author David.W
 */
public class ConnectionCommand extends AbstractCommand {

    public static final ConnectionCommand AUTH   = new ConnectionCommand(new String[]{"AUTH"},  "1.0.0");
    public static final ConnectionCommand ECHO   = new ConnectionCommand(new String[]{"ECHO"},  "1.0.0");
    public static final ConnectionCommand PING   = new ConnectionCommand(new String[]{"PING"},  "1.0.0");
    public static final ConnectionCommand QUIT   = new ConnectionCommand(new String[]{"QUIT"},  "1.0.0");
    public static final ConnectionCommand SELECT = new ConnectionCommand(new String[]{"SELECT"},"1.0.0");
    public static final ConnectionCommand SWAPDB = new ConnectionCommand(new String[]{"SWAPDB"},"4.0.0");

    private ConnectionCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
