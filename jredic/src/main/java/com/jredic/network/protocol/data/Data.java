package com.jredic.network.protocol.data;

/**
 * Interface of redis data.
 *
 * @author David.W
 *
 * @see DataType
 */
public interface Data {

    /**
     * get data type.
     * <p>the type of redis data depends on the first byte.
     *
     * @return
     */
    DataType getType();

}
