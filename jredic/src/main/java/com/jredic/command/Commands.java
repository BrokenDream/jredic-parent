package com.jredic.command;

import com.jredic.network.protocol.data.ArraysData;
import com.jredic.network.protocol.data.BulkStringsData;
import com.jredic.network.protocol.data.Data;
import com.jredic.util.Strings;
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
            elements.add(new BulkStringsData(Strings.decodeByUTF8(command)));
        }
        for(String arg : args){
            elements.add(new BulkStringsData(Strings.decodeByUTF8(arg)));
        }
        return new ArraysData(elements);
    }

}
