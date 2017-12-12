package com.jredic.command;

/**
 * The interface of redis <b>cluster</b> commands.
 * See <a href="https://redis.io/commands#cluster">Redis Cluster Commands</a>.
 *
 * @author David.W
 */
public class ClusterCommand extends AbstractCommand {

    public static final ClusterCommand CLUSTER_ADDSLOTS              = new ClusterCommand(new String[]{"CLUSTER", "ADDSLOTS"},               "3.0.0");
    public static final ClusterCommand CLUSTER_COUNT_FAILURE_REPORTS = new ClusterCommand(new String[]{"CLUSTER", "COUNT-FAILURE-REPORTS"},  "3.0.0");
    public static final ClusterCommand CLUSTER_COUNTKEYSINSLOT       = new ClusterCommand(new String[]{"CLUSTER", "COUNTKEYSINSLOT"},        "3.0.0");
    public static final ClusterCommand CLUSTER_DELSLOTS              = new ClusterCommand(new String[]{"CLUSTER", "DELSLOTS"},               "3.0.0");
    public static final ClusterCommand CLUSTER_FAILOVER              = new ClusterCommand(new String[]{"CLUSTER", "FAILOVER"},               "3.0.0");
    public static final ClusterCommand CLUSTER_FORGET                = new ClusterCommand(new String[]{"CLUSTER", "FORGET"},                 "3.0.0");
    public static final ClusterCommand CLUSTER_GETKEYSINSLOT         = new ClusterCommand(new String[]{"CLUSTER", "GETKEYSINSLOT"},          "3.0.0");
    public static final ClusterCommand CLUSTER_INFO                  = new ClusterCommand(new String[]{"CLUSTER", "INFO"},                   "3.0.0");
    public static final ClusterCommand CLUSTER_KEYSLOT               = new ClusterCommand(new String[]{"CLUSTER", "KEYSLOT"},                "3.0.0");
    public static final ClusterCommand CLUSTER_MEET                  = new ClusterCommand(new String[]{"CLUSTER", "MEET"},                   "3.0.0");
    public static final ClusterCommand CLUSTER_NODES                 = new ClusterCommand(new String[]{"CLUSTER", "NODES"},                  "3.0.0");
    public static final ClusterCommand CLUSTER_REPLICATE             = new ClusterCommand(new String[]{"CLUSTER", "REPLICATE"},              "3.0.0");
    public static final ClusterCommand CLUSTER_RESET                 = new ClusterCommand(new String[]{"CLUSTER", "RESET"},                  "3.0.0");
    public static final ClusterCommand CLUSTER_SAVECONFIG            = new ClusterCommand(new String[]{"CLUSTER", "SAVECONFIG"},             "3.0.0");
    public static final ClusterCommand CLUSTER_SET_CONFIG_EPOCH      = new ClusterCommand(new String[]{"CLUSTER", "SET-CONFIG-EPOCH"},       "3.0.0");
    public static final ClusterCommand CLUSTER_SETSLOT               = new ClusterCommand(new String[]{"CLUSTER", "SETSLOT"},                "3.0.0");
    public static final ClusterCommand CLUSTER_SLAVES                = new ClusterCommand(new String[]{"CLUSTER", "SLAVES"},                 "3.0.0");
    public static final ClusterCommand CLUSTER_SLOTS                 = new ClusterCommand(new String[]{"CLUSTER", "SLOTS"},                  "3.0.0");
    public static final ClusterCommand READONLY                      = new ClusterCommand(new String[]{"READONLY"},                          "3.0.0");
    public static final ClusterCommand READWRITE                     = new ClusterCommand(new String[]{"READWRITE"},                         "3.0.0");

    private ClusterCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
