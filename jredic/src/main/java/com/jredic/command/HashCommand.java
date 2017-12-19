package com.jredic.command;

/**
 * The interface of redis <b>hash</b> commands.
 * See <a href="https://redis.io/commands#hash">Redis Hash Commands</a>.
 *
 * @author David.W
 */
public class HashCommand extends AbstractCommand {

    public static final HashCommand HDEL         = new HashCommand(new String[]{"HDEL"},        "2.0.0");
    public static final HashCommand HDEL_MU      = new HashCommand(new String[]{"HDEL"},        "2.4",  "with multiple fields");
    public static final HashCommand HEXISTS      = new HashCommand(new String[]{"HEXISTS"},     "2.0.0");
    public static final HashCommand HGET         = new HashCommand(new String[]{"HGET"},        "2.0.0");
    public static final HashCommand HGETALL      = new HashCommand(new String[]{"HGETALL"},     "2.0.0");
    public static final HashCommand HINCRBY      = new HashCommand(new String[]{"HINCRBY"},     "2.0.0");
    public static final HashCommand HINCRBYFLOAT = new HashCommand(new String[]{"HINCRBYFLOAT"},"2.6.0");
    public static final HashCommand HKEYS        = new HashCommand(new String[]{"HKEYS"},       "2.0.0");
    public static final HashCommand HLEN         = new HashCommand(new String[]{"HLEN"},        "2.0.0");
    public static final HashCommand HMGET        = new HashCommand(new String[]{"HMGET"},       "2.0.0");
    public static final HashCommand HMSET        = new HashCommand(new String[]{"HMSET"},       "2.0.0");
    public static final HashCommand HSCAN        = new HashCommand(new String[]{"HSCAN"},       "2.8.0");
    public static final HashCommand HSET         = new HashCommand(new String[]{"HSET"},        "2.0.0");
    public static final HashCommand HSETNX       = new HashCommand(new String[]{"HSETNX"},      "2.0.0");
    public static final HashCommand HSTRLEN      = new HashCommand(new String[]{"HSTRLEN"},     "3.2.0");
    public static final HashCommand HVALS        = new HashCommand(new String[]{"HVALS"},       "2.0.0");

    private HashCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

    private HashCommand(String[] values, String startVersion, String addition) {
        super(values, startVersion, addition);
    }
}
