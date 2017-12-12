package com.jredic.command;

/**
 * The interface of redis <b>key</b> commands.
 * See <a href="https://redis.io/commands#key">Redis Key Commands</a>.
 *
 * @author David.W
 */
public class KeyCommand extends AbstractCommand {

    public static final KeyCommand DEL       = new KeyCommand(new String[]{"DEL"},       "1.0.0");
    public static final KeyCommand DUMP      = new KeyCommand(new String[]{"DUMP"},      "2.6.0");
    public static final KeyCommand EXISTS    = new KeyCommand(new String[]{"EXISTS"},    "1.0.0");
    public static final KeyCommand EXPIRE    = new KeyCommand(new String[]{"EXPIRE"},    "1.0.0");
    public static final KeyCommand EXPIREAT  = new KeyCommand(new String[]{"EXPIREAT"},  "1.2.0");
    public static final KeyCommand KEYS      = new KeyCommand(new String[]{"KEYS"},      "1.0.0");
    public static final KeyCommand MIGRATE   = new KeyCommand(new String[]{"MIGRATE"},   "2.6.0");
    public static final KeyCommand MOVE      = new KeyCommand(new String[]{"MOVE"},      "1.0.0");
    public static final KeyCommand OBJECT    = new KeyCommand(new String[]{"OBJECT"},    "2.2.3");
    public static final KeyCommand PERSIST   = new KeyCommand(new String[]{"PERSIST"},   "2.2.0");
    public static final KeyCommand PEXPIRE   = new KeyCommand(new String[]{"PEXPIRE"},   "2.6.0");
    public static final KeyCommand PEXPIREAT = new KeyCommand(new String[]{"PEXPIREAT"}, "2.6.0");
    public static final KeyCommand PTTL      = new KeyCommand(new String[]{"PTTL"},      "2.6.0");
    public static final KeyCommand RANDOMKEY = new KeyCommand(new String[]{"RANDOMKEY"}, "1.0.0");
    public static final KeyCommand RENAME    = new KeyCommand(new String[]{"RENAME"},    "1.0.0");
    public static final KeyCommand RENAMENX  = new KeyCommand(new String[]{"RENAMENX"},  "1.0.0");
    public static final KeyCommand RESTORE   = new KeyCommand(new String[]{"RESTORE"},   "2.6.0");
    public static final KeyCommand SCAN      = new KeyCommand(new String[]{"SCAN"},      "2.8.0");
    public static final KeyCommand SORT      = new KeyCommand(new String[]{"SORT"},      "1.0.0");
    public static final KeyCommand TOUCH     = new KeyCommand(new String[]{"TOUCH"},     "3.2.1");
    public static final KeyCommand TTL       = new KeyCommand(new String[]{"TTL"},       "1.0.0");
    public static final KeyCommand TYPE      = new KeyCommand(new String[]{"TYPE"},      "1.0.0");
    public static final KeyCommand UNLINK    = new KeyCommand(new String[]{"UNLINK"},    "4.0.0");
    public static final KeyCommand WAIT      = new KeyCommand(new String[]{"WAIT"},      "3.0.0");

    private KeyCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
