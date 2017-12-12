package com.jredic.command;

/**
 * The interface of redis <b>sorted set</b> commands.
 * See <a href="https://redis.io/commands#sorted_set">Redis Sorted Set Commands</a>.
 *
 * @author David.W
 */
public class SortedSetCommand extends AbstractCommand {

    public static final SortedSetCommand ZADD             = new SortedSetCommand(new String[]{"ZADD"},            "1.2.0");
    public static final SortedSetCommand ZCARD            = new SortedSetCommand(new String[]{"ZCARD"},           "1.2.0");
    public static final SortedSetCommand ZCOUNT           = new SortedSetCommand(new String[]{"ZCOUNT"},          "2.0.0");
    public static final SortedSetCommand ZINCRBY          = new SortedSetCommand(new String[]{"ZINCRBY"},         "1.2.0");
    public static final SortedSetCommand ZINTERSTORE      = new SortedSetCommand(new String[]{"ZINTERSTORE"},     "2.0.0");
    public static final SortedSetCommand ZLEXCOUNT        = new SortedSetCommand(new String[]{"ZLEXCOUNT"},       "2.8.9");
    public static final SortedSetCommand ZRANGE           = new SortedSetCommand(new String[]{"ZRANGE"},          "1.2.0");
    public static final SortedSetCommand ZRANGEBYLEX      = new SortedSetCommand(new String[]{"ZRANGEBYLEX"},     "2.8.9");
    public static final SortedSetCommand ZRANGEBYSCORE    = new SortedSetCommand(new String[]{"ZRANGEBYSCORE"},   "1.0.5");
    public static final SortedSetCommand ZRANK            = new SortedSetCommand(new String[]{"ZRANK"},           "2.0.0");
    public static final SortedSetCommand ZREM             = new SortedSetCommand(new String[]{"ZREM"},            "1.2.0");
    public static final SortedSetCommand ZREMRANGEBYLEX   = new SortedSetCommand(new String[]{"ZREMRANGEBYLEX"},  "2.8.9");
    public static final SortedSetCommand ZREMRANGEBYRANK  = new SortedSetCommand(new String[]{"ZREMRANGEBYRANK"}, "2.0.0");
    public static final SortedSetCommand ZREMRANGEBYSCORE = new SortedSetCommand(new String[]{"ZREMRANGEBYSCORE"},"1.2.0");
    public static final SortedSetCommand ZREVRANGE        = new SortedSetCommand(new String[]{"ZREVRANGE"},       "1.2.0");
    public static final SortedSetCommand ZREVRANGEBYLEX   = new SortedSetCommand(new String[]{"ZREVRANGEBYLEX"},  "2.8.9");
    public static final SortedSetCommand ZREVRANGEBYSCORE = new SortedSetCommand(new String[]{"ZREVRANGEBYSCORE"},"2.2.0");
    public static final SortedSetCommand ZREVRANK         = new SortedSetCommand(new String[]{"ZREVRANK"},        "2.0.0");
    public static final SortedSetCommand ZSCAN            = new SortedSetCommand(new String[]{"ZSCAN"},           "2.8.0");
    public static final SortedSetCommand ZSCORE           = new SortedSetCommand(new String[]{"ZSCORE"},          "1.2.0");
    public static final SortedSetCommand ZUNIONSTORE      = new SortedSetCommand(new String[]{"ZUNIONSTORE"},     "2.0.0");

    private SortedSetCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
