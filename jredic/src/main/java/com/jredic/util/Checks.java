package com.jredic.util;

import com.jredic.exception.IllegalParameterException;

/**
 * Utility methods for checking preconditions.
 *
 * @author David.W
 */
public class Checks {

    /**
     * check if the given param is null.
     *
     * @param param the dest param to check.
     * @param expression if param is null, expression will show as a error message.
     * @throws throw IllegalParameterException if param is null.
     */
    public static void checkNotNull(Object param, String expression){
        if(param == null){
            throw new IllegalParameterException(expression);
        }
    }

    /**
     * check if the given String param is blank.
     *
     * @param param the dest param to check.
     * @param expression if param is blank, expression will show as a error message.
     * @throws IllegalParameterException if param is blank.
     */
    public static void checkNotBlank(String param, String expression){
        if(param == null || param.trim().length() == 0){
            throw new IllegalParameterException(expression);
        }
    }

    /**
     * check if the given String param array is empty.
     *
     * @param params the dest params to check.
     * @param expression if params is empty, expression will show as a error message.
     * @throws IllegalParameterException if params is empty.
     */
    public static void checkArrayNotEmpty(String[] params, String expression){
        if(params == null || params.length == 0){
            throw new IllegalParameterException(expression);
        }
    }

    public static void checkTrue(boolean condition, String expression){
        if(!condition){
            throw new IllegalParameterException(expression);
        }
    }

    private Checks(){}

}
