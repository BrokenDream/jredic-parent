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

    //the addition.
    private String addition;

    protected AbstractCommand(String[] values, String startVersion) {
        this.values = values;
        this.startVersion = startVersion;
    }

    protected AbstractCommand(String[] values, String startVersion, String addition) {
        this.values = values;
        this.startVersion = startVersion;
        this.addition = addition;
    }

    @Override
    public String[] values() {
        return values;
    }

    @Override
    public String startVersion() {
        return startVersion;
    }

    @Override
    public String addition() {
        return addition;
    }

}
