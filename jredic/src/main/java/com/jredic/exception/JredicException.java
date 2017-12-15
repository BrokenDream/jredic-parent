package com.jredic.exception;

/**
 * The exception for Jredic.
 *
 * @author David.W
 */
public class JredicException extends RuntimeException {

    public JredicException() {
    }

    public JredicException(String message) {
        super(message);
    }

    public JredicException(String message, Throwable cause) {
        super(message, cause);
    }

    public JredicException(Throwable cause) {
        super(cause);
    }

}
