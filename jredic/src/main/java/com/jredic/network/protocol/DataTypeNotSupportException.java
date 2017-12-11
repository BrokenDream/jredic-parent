package com.jredic.network.protocol;

import com.jredic.JredicException;
import com.jredic.command.Command;
import com.jredic.network.protocol.data.DataType;

/**
 * Thrown to indicate that a ResponseHandler can't support given DataType.
 *
 * @author David.W
 */
public class DataTypeNotSupportException extends JredicException {

    public DataTypeNotSupportException(DataType dataType, Command command) {
        super(dataType.name() + " is not support for the cmd ["+command+"]!");
    }

}
