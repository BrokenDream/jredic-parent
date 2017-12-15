package com.jredic.command;

/**
 * A Base implementation of a command.
 *
 * @author David.W
 */
public abstract class AbstractCommand implements Command {

    //the string commands.
    private String[] values;

    //the start version.
    private String startVersion;

    protected AbstractCommand(String[] values, String startVersion) {
        this.values = values;
    }

    @Override
    public String[] values() {
        return values;
    }

    @Override
    public String startVersion() {
        return startVersion;
    }

}
