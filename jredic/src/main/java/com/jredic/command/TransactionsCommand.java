package com.jredic.command;

/**
 * The interface of redis <b>transactions</b> commands.
 * See <a href="https://redis.io/commands#transactions">Redis Transactions Commands</a>.
 *
 * @author David.W
 */
public class TransactionsCommand extends AbstractCommand {

    public static final TransactionsCommand DISCARD = new TransactionsCommand(new String[]{"DISCARD"},"2.0.0");
    public static final TransactionsCommand EXEC    = new TransactionsCommand(new String[]{"EXEC"},   "1.2.0");
    public static final TransactionsCommand MULTI   = new TransactionsCommand(new String[]{"MULTI"},  "1.2.0");
    public static final TransactionsCommand UNWATCH = new TransactionsCommand(new String[]{"UNWATCH"},"2.2.0");
    public static final TransactionsCommand WATCH   = new TransactionsCommand(new String[]{"WATCH"},  "2.2.0");

    private TransactionsCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
