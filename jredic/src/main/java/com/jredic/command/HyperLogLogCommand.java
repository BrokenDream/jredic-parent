package com.jredic.command;

/**
 * The interface of redis <b>hyperloglog</b> commands.
 * See <a href="https://redis.io/commands#hyperloglog">Redis HyperLogLog Commands</a>.
 *
 * @author David.W
 */
public class HyperLogLogCommand extends AbstractCommand {

    public static final HyperLogLogCommand PFADD   = new HyperLogLogCommand(new String[]{"PFADD"},  "2.8.9");
    public static final HyperLogLogCommand PFCOUNT = new HyperLogLogCommand(new String[]{"PFCOUNT"},"2.8.9");
    public static final HyperLogLogCommand PFMERGE = new HyperLogLogCommand(new String[]{"PFMERGE"},"2.8.9");

    private HyperLogLogCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
