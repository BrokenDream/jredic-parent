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
     * Start the client.
     */
    void start();

    /**
     * Stop the client.
     */
    void stop();

    /**
     * Send the Request data and return the Response data.
     *
     * @param request request data.
     * @return
     *      response data.
     */
    Data send(ArraysData request) throws JredicException;

}
