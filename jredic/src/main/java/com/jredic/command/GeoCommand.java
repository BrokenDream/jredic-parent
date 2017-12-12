package com.jredic.command;

/**
 * The interface of redis <b>geo</b> commands.
 * See <a href="https://redis.io/commands#geo">Redis Geo Commands</a>.
 *
 * @author David.W
 */
public class GeoCommand extends AbstractCommand {

    public static final GeoCommand GEOADD            = new GeoCommand(new String[]{"GEOADD"},           "3.2.0");
    public static final GeoCommand GEODIST           = new GeoCommand(new String[]{"GEODIST"},          "3.2.0");
    public static final GeoCommand GEOHASH           = new GeoCommand(new String[]{"GEOHASH"},          "3.2.0");
    public static final GeoCommand GEOPOS            = new GeoCommand(new String[]{"GEOPOS"},           "3.2.0");
    public static final GeoCommand GEORADIUS         = new GeoCommand(new String[]{"GEORADIUS"},        "3.2.0");
    public static final GeoCommand GEORADIUSBYMEMBER = new GeoCommand(new String[]{"GEORADIUSBYMEMBER"},"3.2.0");

    private GeoCommand(String[] values, String startVersion) {
        super(values, startVersion);
    }

}
