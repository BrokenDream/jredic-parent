package com.jredic.exception;

/**
 * Throws when passing the illegal parameters towards Redis Command.
 *
 * @author David.W
 */
public class IllegalParameterException extends JredicException {

    public IllegalParameterException() {
    }

    public IllegalParameterException(String message) {
        super(message);
    }

    public IllegalParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParameterException(Throwable cause) {
        super(cause);
    }

}
