package com.jredic.command;

/**
 * The interface of redis <b>list</b> commands.
 * See <a href="https://redis.io/commands#list">Redis List Commands</a>.
 *
 * @author David.W
 */
public class ListCommand extends AbstractCommand {

    public static final ListCommand BLPOP      = new ListCommand(new String[]{"BLPOP"},     "2.0.0");
    public static final ListCommand BRPOP      = new ListCommand(new String[]{"BRPOP"},     "2.0.0");
    public static final ListCommand BRPOPLPUSH = new ListCommand(new String[]{"BRPOPLPUSH"},"2.2.0");
    public static final ListCommand LINDEX     = new ListCommand(new String[]{"LINDEX"},    "1.0.0");
    public static final ListCommand LINSERT    = new ListCommand(new String[]{"LINSERT"},   "2.2.0");
    public static final ListCommand LLEN       = new ListCommand(new String[]{"LLEN"},      "1.0.0");
    public static final ListCommand LPOP       = new ListCommand(new String[]{"LPOP"},      "1.0.0");
    public static final ListCommand LPUSH      = new ListCommand(new String[]{"LPUSH"},     "1.0.0");
    public static final ListCommand LPUSHX     = new ListCommand(new String[]{"LPUSHX"},    "2.2.0");
    public static final ListCommand LRANGE     = new ListCommand(new String[]{"LRANGE"},    "1.0.0");
    public static final ListCommand LREM       = new ListCommand(new String[]{"LREM"},      "1.0.0");
    public static final ListCommand LSET       = new ListCommand(new String[]{"LSET"},      "1.0.0");
    public static final ListCommand LTRIM      = new ListCommand(new String[]{"LTRIM"},     "1.0.0");
    public static final ListCommand RPOP       = new ListCommand(new String[]{"RPOP"},      "1.0.0");
    public static final ListCommand RPOPLPUSH  = new ListCommand(new String[]{"RPOPLPUSH"}, "1.2.0");
    public static final ListCommand RPUSH      = new ListCommand(new String[]{"RPUSH"},     "1.0.0");
    public static final ListCommand RPUSHX     = new ListCommand(new String[]{"RPUSHX"},    "2.2.0");

    private ListCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
