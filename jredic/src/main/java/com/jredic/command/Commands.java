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
     * if the Start Version is X.Y.Z, then the Value is 'X*1000 + Y*100 + Z'.
     *
     * @param startVersion a version type of String.
     * @return
     *      a int value represents the Start Version.
     */
    static int getValueFromStartVersion(String startVersion){
        String[] values = startVersion.split("\\.");
        return Integer.parseInt(values[0]) * 10000 + Integer.parseInt(values[1]) * 100 + Integer.parseInt(values[2]);
    }

}
