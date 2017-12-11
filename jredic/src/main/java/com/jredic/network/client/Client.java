package com.jredic.network.client;

import com.jredic.JredicException;
import com.jredic.network.protocol.data.ArraysData;
import com.jredic.network.protocol.data.Data;

/**
 * The client of network layer
 *
 * @author David.W
 */
public interface Client {

    /**
     * start the client
     */
    void start();

    /**
     * stop the client
     */
    void stop();

    /**
     * send data
     *
     * @param request
     * @return
     */
    Data send(ArraysData request) throws JredicException;

}
