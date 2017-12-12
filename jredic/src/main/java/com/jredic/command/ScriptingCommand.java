package com.jredic.command;

/**
 * The interface of redis <b>scripting</b> commands.
 * See <a href="https://redis.io/commands#scripting">Redis Scripting Commands</a>.
 *
 * @author David.W
 */
public class ScriptingCommand extends AbstractCommand {

    public static final ScriptingCommand EVAL          = new ScriptingCommand(new String[]{"EVAL"},              "2.6.0");
    public static final ScriptingCommand EVALSHA       = new ScriptingCommand(new String[]{"EVALSHA"},           "2.6.0");
    public static final ScriptingCommand SCRIPT_DEBUG  = new ScriptingCommand(new String[]{"SCRIPT", "DEBUG"},   "3.2.0");
    public static final ScriptingCommand SCRIPT_EXISTS = new ScriptingCommand(new String[]{"SCRIPT", "EXISTS"},  "2.6.0");
    public static final ScriptingCommand SCRIPT_FLUSH  = new ScriptingCommand(new String[]{"SCRIPT", "FLUSH"},   "2.6.0");
    public static final ScriptingCommand SCRIPT_KILL   = new ScriptingCommand(new String[]{"SCRIPT", "KILL"},    "2.6.0");
    public static final ScriptingCommand SCRIPT_LOAD   = new ScriptingCommand(new String[]{"SCRIPT", "LOAD"},    "2.6.0");

    private ScriptingCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
