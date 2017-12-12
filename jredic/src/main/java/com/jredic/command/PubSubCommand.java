package com.jredic.command;

/**
 * The interface of redis <b>pub/sub</b> commands.
 * See <a href="https://redis.io/commands#pubsub">Redis Pub/Sub Commands</a>.
 *
 * @author David.W
 */
public class PubSubCommand extends AbstractCommand {

    public static final PubSubCommand PSUBSCRIBE   = new PubSubCommand(new String[]{"PSUBSCRIBE"},  "2.0.0");
    public static final PubSubCommand PUBLISH      = new PubSubCommand(new String[]{"PUBLISH"},     "2.0.0");
    public static final PubSubCommand PUBSUB       = new PubSubCommand(new String[]{"PUBSUB"},      "2.8.0");
    public static final PubSubCommand PUNSUBSCRIBE = new PubSubCommand(new String[]{"PUNSUBSCRIBE"},"2.0.0");
    public static final PubSubCommand SUBSCRIBE    = new PubSubCommand(new String[]{"SUBSCRIBE"},   "2.0.0");
    public static final PubSubCommand UNSUBSCRIBE  = new PubSubCommand(new String[]{"UNSUBSCRIBE"}, "2.0.0");

    private PubSubCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
