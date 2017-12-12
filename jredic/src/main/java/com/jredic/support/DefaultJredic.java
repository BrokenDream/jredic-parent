package com.jredic.support;

import com.jredic.Jredic;
import com.jredic.JredicException;
import com.jredic.RedisDataType;
import com.jredic.command.Command;
import com.jredic.command.Commands;
import com.jredic.command.KeyCommand;
import com.jredic.command.StringCommand;
import com.jredic.network.protocol.DataTypeNotSupportException;
import com.jredic.network.client.Client;
import com.jredic.network.protocol.data.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation for Jredic.
 *
 * @author David.W
 */
public class DefaultJredic implements Jredic {

    private Client client;


    @Override
    public long del(String... keys) {
        return process(KeyCommand.DEL, LONG_ACTION, keys);
    }

    @Override
    public boolean del(String key) {
        long number = process(KeyCommand.DEL, LONG_ACTION, key);
        return number != 0;
    }

    @Override
    public String dump(String key) {
        return process(KeyCommand.DUMP, STRING_ACTION, key);
    }

    @Override
    public boolean exists(String key) {
        return process(KeyCommand.EXISTS, BOOLEAN_ACTION, key);
    }

    @Override
    public boolean expire(String key, int seconds) {
        return process(KeyCommand.EXPIRE, BOOLEAN_ACTION, key, Integer.toString(seconds));
    }

    @Override
    public boolean expireAt(String key, long unixTimeInSeconds) {
        return process(KeyCommand.EXPIREAT, BOOLEAN_ACTION, key, Long.toString(unixTimeInSeconds));
    }

    @Override
    public boolean pexpire(String key, long millis) {
        return process(KeyCommand.PEXPIRE, BOOLEAN_ACTION, key, Long.toString(millis));
    }

    @Override
    public boolean pexpireAt(String key, long unixTimeInMillis) {
        return process(KeyCommand.PEXPIREAT, BOOLEAN_ACTION, key, Long.toString(unixTimeInMillis));
    }

    @Override
    public List<String> keys(String pattern) {
        return process(KeyCommand.KEYS, ARRAY_ACTION, pattern);
    }

    @Override
    public boolean move(String key, int dbIndex) {
        return process(KeyCommand.MOVE, BOOLEAN_ACTION, key, Integer.toString(dbIndex));
    }

    @Override
    public boolean persist(String key) {
        return process(KeyCommand.PERSIST, BOOLEAN_ACTION, key);
    }

    @Override
    public long ttl(String key) {
        return process(KeyCommand.TTL, LONG_ACTION, key);
    }

    @Override
    public long pttl(String key) {
        return process(KeyCommand.PTTL, LONG_ACTION, key);
    }

    @Override
    public String randomKey() {
        return process(KeyCommand.RANDOMKEY, STRING_ACTION);
    }

    @Override
    public void rename(String key, String newKey) {
        process(KeyCommand.RENAME, OK_ACTION, key, newKey);
    }

    @Override
    public boolean renamenx(String key, String newKey) {
        return process(KeyCommand.RENAMENX, BOOLEAN_ACTION, key, newKey);
    }

    @Override
    public RedisDataType type(String key) {
        return process(KeyCommand.TYPE, new Action<RedisDataType>() {
            @Override
            public RedisDataType doAction(Data data) throws JredicException {
                if(DataType.SIMPLE_STRINGS.equals(data.getType())){
                    return RedisDataType.get(((SimpleStringsData)data).getContent());
                }
                throw ACTION_EXCEPTION;
            }
        }, key);
    }


    //common process
    private <R> R process(Command cmd, Action<R> action, String ... args){

        ArraysData request = Commands.createRequest(cmd, args);
        Data response = client.send(request);
        DataType dataType = response.getType();
        try{
            return action.doAction(response);
        }catch (JredicException e){
            if(ACTION_EXCEPTION == e){
                if(DataType.ERRORS.equals(dataType)){
                    throw new JredicException(((ErrorsData) response).getErrorMsg());
                }else{
                    throw new DataTypeNotSupportException(dataType, cmd);
                }
            }else{
                throw e;
            }
        }

    }

    private interface Action<R>{

        R doAction(Data data) throws JredicException;

    }

    private static final Action<Boolean> BOOLEAN_ACTION = new BooleanAction();
    private static final Action<Long> LONG_ACTION = new LongAction();
    private static final Action<String> STRING_ACTION = new StringAction();
    private static final Action<List<String>> ARRAY_ACTION = new ArrayAction();
    private static final Action<String> OK_ACTION = new OkAction();

    private static final JredicException ACTION_EXCEPTION = new JredicException();

    /*
     * for long type number.
     */
    private static class LongAction implements Action<Long>{

        @Override
        public Long doAction(Data data) throws JredicException{
            if(DataType.INTEGERS.equals(data.getType())){
                return ((IntegersData) data).getValue();
            }
            throw ACTION_EXCEPTION;
        }

    }

    /*
     * for String type like Content.
     */
    private static class StringAction implements Action<String>{

        @Override
        public String doAction(Data data) throws JredicException{
            if(DataType.BULK_STRINGS.equals(data.getType())){
                return ((BulkStringsData) data).getContent();
            }
            throw ACTION_EXCEPTION;
        }
    }

    /*
     * 1 indicates true and 0 indicates false.
     */
    private static class BooleanAction implements Action<Boolean>{

        @Override
        public Boolean doAction(Data data) throws JredicException{
            if(DataType.INTEGERS.equals(data.getType())){
                IntegersData result = (IntegersData) data;
                if(result.getValue() == 0){
                    return false;
                }else{
                    return true;
                }
            }
            throw ACTION_EXCEPTION;
        }

    }

    private static class OkAction implements Action<String>{

        private static final String OK = "OK";

        @Override
        public String doAction(Data data) throws JredicException{
            if(DataType.SIMPLE_STRINGS.equals(data.getType())){
                SimpleStringsData simpleStringsData = (SimpleStringsData) data;
                if(OK.equals(simpleStringsData.getContent())){
                    return OK;
                }else{
                    throw new JredicException(simpleStringsData.getContent());
                }
            }
            throw ACTION_EXCEPTION;
        }
    }

    /*
     * for Arrays type.
     */
    private static class ArrayAction implements Action<List<String>>{

        @Override
        public List<String> doAction(Data data) throws JredicException{
            if(DataType.ARRAYS.equals(data.getType())){
                ArraysData arraysData = (ArraysData) data;
                List<Data> elements = arraysData.getElements();
                if(elements == null || elements.size() == 0){
                    return null;
                }else{
                    List<String> keyList = new ArrayList<>(elements.size());
                    for(Data element : elements){
                        keyList.add(((BulkStringsData)element).getContent());
                    }
                    return keyList;
                }
            }
            throw ACTION_EXCEPTION;
        }

    }



    @Override
    public void set(String key, String value) {
        process(StringCommand.SET, OK_ACTION, key, value);
    }

    @Override
    public String get(String key) {
        return process(StringCommand.GET, STRING_ACTION, key);
    }


    public void setClient(Client client) {
        this.client = client;
    }

}
