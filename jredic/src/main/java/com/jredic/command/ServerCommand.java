package com.jredic.command;

/**
 * The interface of redis <b>server</b> commands.
 * See <a href="https://redis.io/commands#server">Redis Server Commands</a>.
 *
 * @author David.W
 */
public class ServerCommand extends AbstractCommand {

    public static final ServerCommand BGREWRITEAOF     = new ServerCommand(new String[]{"BGREWRITEAOF"},       "1.0.0");
    public static final ServerCommand BGSAVE           = new ServerCommand(new String[]{"BGSAVE"},             "1.0.0");
    public static final ServerCommand CLIENT_GETNAME   = new ServerCommand(new String[]{"CLIENT", "GETNAME"},  "2.6.9");
    public static final ServerCommand CLIENT_KILL      = new ServerCommand(new String[]{"CLIENT", "KILL"},     "2.4.0");
    public static final ServerCommand CLIENT_LIST      = new ServerCommand(new String[]{"CLIENT", "LIST"},     "2.4.0");
    public static final ServerCommand CLIENT_PAUSE     = new ServerCommand(new String[]{"CLIENT", "PAUSE"},    "2.9.50");
    public static final ServerCommand CLIENT_REPLY     = new ServerCommand(new String[]{"CLIENT", "REPLY"},    "3.2.0");
    public static final ServerCommand CLIENT_SETNAME   = new ServerCommand(new String[]{"CLIENT", "SETNAME"},  "2.6.9");
    public static final ServerCommand COMMAND          = new ServerCommand(new String[]{"COMMAND"},            "2.8.13");
    public static final ServerCommand COMMAND_COUNT    = new ServerCommand(new String[]{"COMMAND", "COUNT"},   "2.8.13");
    public static final ServerCommand COMMAND_GETKEYS  = new ServerCommand(new String[]{"COMMAND", "GETKEYS"}, "2.8.13");
    public static final ServerCommand COMMAND_INFO     = new ServerCommand(new String[]{"COMMAND", "INFO"},    "2.8.13");
    public static final ServerCommand CONFIG_GET       = new ServerCommand(new String[]{"CONFIG", "GET"},      "2.0.0");
    public static final ServerCommand CONFIG_RESETSTAT = new ServerCommand(new String[]{"CONFIG", "RESETSTAT"},"2.0.0");
    public static final ServerCommand CONFIG_REWRITE   = new ServerCommand(new String[]{"CONFIG", "REWRITE"},  "2.8.0");
    public static final ServerCommand CONFIG_SET       = new ServerCommand(new String[]{"CONFIG", "SET"},      "2.0.0");
    public static final ServerCommand DBSIZE           = new ServerCommand(new String[]{"DBSIZE"},             "1.0.0");
    public static final ServerCommand DEBUG_OBJECT     = new ServerCommand(new String[]{"DEBUG", "OBJECT"},    "1.0.0");
    public static final ServerCommand DEBUG_SEGFAULT   = new ServerCommand(new String[]{"DEBUG", "SEGFAULT"},  "1.0.0");
    public static final ServerCommand FLUSHALL         = new ServerCommand(new String[]{"FLUSHALL"},           "1.0.0");
    public static final ServerCommand FLUSHDB          = new ServerCommand(new String[]{"FLUSHDB"},            "1.0.0");
    public static final ServerCommand INFO             = new ServerCommand(new String[]{"INFO"},               "1.0.0");
    public static final ServerCommand LASTSAVE         = new ServerCommand(new String[]{"LASTSAVE"},           "1.0.0");
    public static final ServerCommand MONITOR          = new ServerCommand(new String[]{"MONITOR"},            "1.0.0");
    public static final ServerCommand ROLE             = new ServerCommand(new String[]{"ROLE"},               "2.8.12");
    public static final ServerCommand SAVE             = new ServerCommand(new String[]{"SAVE"},               "1.0.0");
    public static final ServerCommand SHUTDOWN         = new ServerCommand(new String[]{"SHUTDOWN"},           "1.0.0");
    public static final ServerCommand SLAVEOF          = new ServerCommand(new String[]{"SLAVEOF"},            "1.0.0");
    public static final ServerCommand SLOWLOG          = new ServerCommand(new String[]{"SLOWLOG"},            "2.2.12");
    public static final ServerCommand SYNC             = new ServerCommand(new String[]{"SYNC"},               "1.0.0");
    public static final ServerCommand TIME             = new ServerCommand(new String[]{"TIME"},               "1.0.0");

    private ServerCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
