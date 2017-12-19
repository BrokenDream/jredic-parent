package com.jredic.command;

/**
 * The interface of redis <b>string</b> commands.
 * See <a href="https://redis.io/commands#string">Redis String Commands</a>.
 *
 * @author David.W
 */
public class StringCommand extends AbstractCommand{

    public static final StringCommand APPEND      = new StringCommand(new String[]{"APPEND"},      "2.0.0");
    public static final StringCommand BITCOUNT    = new StringCommand(new String[]{"BITCOUNT"},    "2.6.0");
    public static final StringCommand BITFIELD    = new StringCommand(new String[]{"BITFIELD"},    "3.2.0");
    public static final StringCommand BITOP       = new StringCommand(new String[]{"BITOP"},       "2.6.0");
    public static final StringCommand BITPOS      = new StringCommand(new String[]{"BITPOS"},      "2.8.7");
    public static final StringCommand DECR        = new StringCommand(new String[]{"DECR"},        "1.0.0");
    public static final StringCommand DECRBY      = new StringCommand(new String[]{"DECRBY"},      "1.0.0");
    public static final StringCommand GET         = new StringCommand(new String[]{"GET"},         "1.0.0");
    public static final StringCommand GETBIT      = new StringCommand(new String[]{"GETBIT"},      "2.2.0");
    public static final StringCommand GETRANGE    = new StringCommand(new String[]{"GETRANGE"},    "2.4.0");
    public static final StringCommand GETSET      = new StringCommand(new String[]{"GETSET"},      "1.0.0");
    public static final StringCommand INCR        = new StringCommand(new String[]{"INCR"},        "1.0.0");
    public static final StringCommand INCRBY      = new StringCommand(new String[]{"INCRBY"},      "1.0.0");
    public static final StringCommand INCRBYFLOAT = new StringCommand(new String[]{"INCRBYFLOAT"}, "2.6.0");
    public static final StringCommand MGET        = new StringCommand(new String[]{"MGET"},        "1.0.0");
    public static final StringCommand MSET        = new StringCommand(new String[]{"MSET"},        "1.0.1");
    public static final StringCommand MSETNX      = new StringCommand(new String[]{"MSETNX"},      "1.0.1");
    public static final StringCommand PSETEX      = new StringCommand(new String[]{"PSETEX"},      "2.6.0");
    public static final StringCommand SET         = new StringCommand(new String[]{"SET"},         "1.0.0");
    /*
     * Starting with Redis 2.6.12 SET supports a set of options.
     */
    public static final StringCommand SET_OPTIONS = new StringCommand(new String[]{"SET"},         "2.6.12", "with options");
    public static final StringCommand SETBIT      = new StringCommand(new String[]{"SETBIT"},      "2.2.0");
    public static final StringCommand SETEX       = new StringCommand(new String[]{"SETEX"},       "2.0.0");
    public static final StringCommand SETNX       = new StringCommand(new String[]{"SETNX"},       "1.0.0");
    public static final StringCommand SETRANGE    = new StringCommand(new String[]{"SETRANGE"},    "2.2.0");
    public static final StringCommand STRLEN      = new StringCommand(new String[]{"STRLEN"},      "2.2.0");

    private StringCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

    private StringCommand(String[] values, String startVersion, String addition) {
        super(values, startVersion, addition);
    }
}
