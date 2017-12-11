package com.jredic.network.protocol.data;

/**
 * @author David.W
 */
public interface DataFactory<D extends Data, K> {

    D createData(K key);

}
