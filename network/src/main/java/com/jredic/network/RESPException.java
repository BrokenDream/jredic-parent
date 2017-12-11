package com.jredic.network;

/**
 * Exception for RESP.
 *
 * @author David.W
 */
public class RESPException extends RuntimeException {

    public RESPException() {
    }

    public RESPException(String message) {
        super(message);
    }

    public RESPException(String message, Throwable cause) {
        super(message, cause);
    }

    public RESPException(Throwable cause) {
        super(cause);
    }

}
