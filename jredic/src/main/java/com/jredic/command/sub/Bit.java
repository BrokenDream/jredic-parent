package com.jredic.command.sub;

/**
 * Argument for Command 'BITPOS'.
 *
 * @author David.W
 */
public enum Bit {

    SET(1),
    CLEAR(0),
    ;

    private int value;

    Bit(int value) {
        this.value = value;
    }

    public int value(){
        return value;
    }
}
