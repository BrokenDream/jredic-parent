package com.jredic.command.sub;

/**
 * Section of command 'INFO'.
 * <p>
 * see {@link com.jredic.command.ServerCommand#INFO}
 *
 * @author David.W
 */
public enum Section {

    SERVER("server"),
    CLIENTS("clients"),
    MEMORY("memory"),
    PERSISTENCE("persistence"),
    STATS("stats"),
    REPLICATION("replication"),
    CPU("cpu"),
    COMMAND_STATS("commandstats"),
    CLUSTER("cluster"),
    KEY_SPACE("keyspace"),

    ALL("all"),
    DEF("default"),
    ;

    private String value;

    Section(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
