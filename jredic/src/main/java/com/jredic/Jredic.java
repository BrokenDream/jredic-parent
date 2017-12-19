package com.jredic;

import com.jredic.exception.JredicException;

/**
 * The core interface of Java Remote Dictionary Client.
 *
 * @author David.W
 */
public interface Jredic extends KeyClient, StringClient, ConnectionClient, HashClient{

    /**
     * For some resources to initialize.
     *
     * @throws JredicException if some err occur while init.
     */
    void init() throws JredicException;

    /**
     * For some resources to destroy.
     *
     * @throws JredicException if some err occur while destroy.
     */
    void destroy() throws JredicException;

}
