package com.jredic.command;

import com.jredic.network.protocol.data.ArraysData;
import com.jredic.network.protocol.data.BulkStringsData;
import com.jredic.network.protocol.data.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods related to command.
 *
 * @author David.W
 */
public class Commands {

    /**
     * Create the Request.
     * according to the RESP, client's request is a Arrays of Bulk Strings.
     *
     * @param cmd the command of request.
     * @param args the arguments of request.
     * @return
     *      a ArraysData representing a request.
     */
    public static ArraysData createRequest(Command cmd, String ... args){
        List<Data> elements = new ArrayList<>(cmd.values().length + args.length);
        for(String command : cmd.values()){
            elements.add(new BulkStringsData(command));
        }
        for(String arg : args){
            elements.add(new BulkStringsData(arg));
        }
        return new ArraysData(elements);
    }

    /**
     * Convert Start Version to a int value.
     * if the Start Version is X.Y.Z, then the Value is 'X*10000000000 + Y*100000 + Z'.
     *
     * @param startVersion a version type of String.
     * @return
     *      a int value represents the Start Version.
     */
    static long getValueFromStartVersion(String startVersion){
        String[] values = startVersion.split("\\.");
        /*
         * today when i download Redis Server (Windows Version),
         * i found some 'Version' like '2.8.2402' ... holy...
         */
        return Long.parseLong(values[0]) * 10000000000L + Long.parseLong(values[1]) * 100000 + Long.parseLong(values[2]);
    }

}
