package com.jredic.network.protocol.data;

/**
 * @author David.W
 */
public class SimpleStringsDataFactory implements DataFactory<SimpleStringsData, String> {

    private static final SimpleStringsData OK = new SimpleStringsData("OK");

    @Override
    public SimpleStringsData createData(String key) {
        if("OK".equals(key)){
            return OK;
        }
        return new SimpleStringsData(key);
    }

}
