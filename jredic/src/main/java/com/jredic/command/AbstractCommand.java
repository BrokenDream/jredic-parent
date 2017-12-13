package com.jredic.command;

/**
 * A Base implementation of a command.
 *
 * @author David.W
 */
public abstract class AbstractCommand implements Command {

    //the string commands.
    private String[] values;

    //the start version value.
    private long svv;

    protected AbstractCommand(String[] values, String startVersion) {
        this.values = values;
        this.svv = Commands.getValueFromStartVersion(startVersion);
    }

    @Override
    public String[] values() {
        return values;
    }

    @Override
    public long svv() {
        return svv;
    }

}
