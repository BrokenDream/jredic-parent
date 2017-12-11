package com.jredic.network.protocol.data;

/**
 * See <a href="https://redis.io/topics/protocol#resp-errors">RESP#resp-errors</a>.
 *
 * @author David.W
 */
public class ErrorsData implements Data {

    private String errorMsg;

    public ErrorsData(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public DataType getType() {
        return DataType.ERRORS;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return "ErrorsData[" + errorMsg + "]";
    }

}
