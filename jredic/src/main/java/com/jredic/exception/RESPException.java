package com.jredic.exception;

/**
 * Exception for RESP.
 *
 * @author David.W
 */
public class RESPException extends JredicException {

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
