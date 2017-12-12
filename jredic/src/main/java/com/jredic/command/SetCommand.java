package com.jredic.command;

/**
 * The interface of redis <b>set</b> commands.
 * See <a href="https://redis.io/commands#set">Redis Set Commands</a>.
 *
 * @author David.W
 */
public class SetCommand extends AbstractCommand {

    public static final SetCommand SADD        = new SetCommand(new String[]{"SADD"},       "1.0.0");
    public static final SetCommand SCARD       = new SetCommand(new String[]{"SCARD"},      "1.0.0");
    public static final SetCommand SDIFF       = new SetCommand(new String[]{"SDIFF"},      "1.0.0");
    public static final SetCommand SDIFFSTORE  = new SetCommand(new String[]{"SDIFFSTORE"}, "1.0.0");
    public static final SetCommand SINTER      = new SetCommand(new String[]{"SINTER"},     "1.0.0");
    public static final SetCommand SINTERSTORE = new SetCommand(new String[]{"SINTERSTORE"},"1.0.0");
    public static final SetCommand SISMEMBER   = new SetCommand(new String[]{"SISMEMBER"},  "1.0.0");
    public static final SetCommand SMEMBERS    = new SetCommand(new String[]{"SMEMBERS"},   "1.0.0");
    public static final SetCommand SMOVE       = new SetCommand(new String[]{"SMOVE"},      "1.0.0");
    public static final SetCommand SPOP        = new SetCommand(new String[]{"SPOP"},       "1.0.0");
    public static final SetCommand SRANDMEMBER = new SetCommand(new String[]{"SRANDMEMBER"},"1.0.0");
    public static final SetCommand SREM        = new SetCommand(new String[]{"SREM"},       "1.0.0");
    public static final SetCommand SSCAN       = new SetCommand(new String[]{"SSCAN"},      "2.8.0");
    public static final SetCommand SUNION      = new SetCommand(new String[]{"SUNION"},     "1.0.0");
    public static final SetCommand SUNIONSTORE = new SetCommand(new String[]{"SUNIONSTORE"},"1.0.0");

    private SetCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
