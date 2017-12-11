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
     * <p>according to the RESP, client's request is a Arrays of Bulk Strings.
     *
     * @param cmd the command of request.
     * @param args the arguments of request.
     * @return
     *      a ArraysData representing a request.
     */
    public static ArraysData createRequest(Command cmd, String ... args){
        List<Data> elements = new ArrayList<>(args.length + 1);
        elements.add(new BulkStringsData(cmd.getCommand()));
        for(String arg : args){
            elements.add(new BulkStringsData(arg));
        }
        return new ArraysData(elements);
    }

    public static int getValueFromSinceVersion(String sinceVersion){
        String[] values = sinceVersion.split("\\.");
        return Integer.parseInt(values[0]) * 100 + Integer.parseInt(values[1]) * 10 + Integer.parseInt(values[2]);
    }

}
