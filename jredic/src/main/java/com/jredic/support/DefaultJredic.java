package com.jredic.support;

import com.jredic.Jredic;
import com.jredic.RedisDataType;
import com.jredic.command.Command;
import com.jredic.command.Commands;
import com.jredic.command.KeyCommand;
import com.jredic.command.StringCommand;
import com.jredic.command.sub.Bit;
import com.jredic.command.sub.BitOP;
import com.jredic.command.sub.SortOptionBuilder;
import com.jredic.exception.DataTypeNotSupportException;
import com.jredic.exception.JredicException;
import com.jredic.network.client.Client;
import com.jredic.network.protocol.data.*;
import com.jredic.util.Checks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation for Jredic.
 *
 * @author David.W
 */
public class DefaultJredic implements Jredic {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJredic.class);

    /*
     * the client for network.
     */
    private Client client;

    @Override
    public long del(String... keys) {
        Checks.checkArrayNotEmpty(keys, "the keys for 'del' is empty!");
        return process(KeyCommand.DEL, LONG_ACTION, keys);
    }

    @Override
    public boolean del(String key) {
        Checks.checkNotBlank(key, "the key for 'del' is blank!");
        long number = process(KeyCommand.DEL, LONG_ACTION, key);
        return number != 0;
    }

    @Override
    public byte[] dump(String key) {
        Checks.checkNotBlank(key, "the key for 'dump' is blank!");
        return process(KeyCommand.DUMP, new Action<byte[]>() {
            @Override
            public byte[] doAction(Data data) throws JredicException {
                if(DataType.BULK_STRINGS.equals(data.getType())){
                    return ((BulkStringsData) data).getContent();
                }
                throw ACTION_EXCEPTION;
            }
        }, key);
    }

    @Override
    public boolean exists(String key) {
        Checks.checkNotBlank(key, "the key for 'exists' is blank!");
        return process(KeyCommand.EXISTS, BOOLEAN_ACTION, key);
    }

    @Override
    public long exists(String firstKey, String... otherKeys) {
        Checks.checkNotBlank(firstKey, "the firstKey for 'exists' is blank!");
        Checks.checkArrayNotEmpty(otherKeys, "the otherKeys for 'exists' is empty!");
        return process(KeyCommand.EXISTS_MU, LONG_ACTION, packageParams(otherKeys, firstKey));
    }

    @Override
    public boolean expire(String key, int seconds) {
        Checks.checkNotBlank(key, "the key for 'expire' is blank!");
        return process(KeyCommand.EXPIRE, BOOLEAN_ACTION, key, Integer.toString(seconds));
    }

    @Override
    public boolean expireAt(String key, long unixTimeInSeconds) {
        Checks.checkNotBlank(key, "the key for 'expireAt' is blank!");
        return process(KeyCommand.EXPIREAT, BOOLEAN_ACTION, key, Long.toString(unixTimeInSeconds));
    }

    @Override
    public boolean pexpire(String key, long millis) {
        Checks.checkNotBlank(key, "the key for 'pexpire' is blank!");
        return process(KeyCommand.PEXPIRE, BOOLEAN_ACTION, key, Long.toString(millis));
    }

    @Override
    public boolean pexpireAt(String key, long unixTimeInMillis) {
        Checks.checkNotBlank(key, "the key for 'pexpireAt' is blank!");
        return process(KeyCommand.PEXPIREAT, BOOLEAN_ACTION, key, Long.toString(unixTimeInMillis));
    }

    @Override
    public List<String> keys(String pattern) {
        Checks.checkNotBlank(pattern, "the pattern for 'keys' is blank!");
        return process(KeyCommand.KEYS, ARRAY_ACTION, pattern);
    }

    @Override
    public boolean move(String key, int dbIndex) {
        Checks.checkNotBlank(key, "the key for 'move' is blank!");
        return process(KeyCommand.MOVE, BOOLEAN_ACTION, key, Integer.toString(dbIndex));
    }

    @Override
    public boolean persist(String key) {
        Checks.checkNotBlank(key, "the key for 'persist' is blank!");
        return process(KeyCommand.PERSIST, BOOLEAN_ACTION, key);
    }

    @Override
    public long ttl(String key) {
        Checks.checkNotBlank(key, "the key for 'ttl' is blank!");
        return process(KeyCommand.TTL, LONG_ACTION, key);
    }

    @Override
    public long pttl(String key) {
        Checks.checkNotBlank(key, "the key for 'pttl' is blank!");
        return process(KeyCommand.PTTL, LONG_ACTION, key);
    }

    @Override
    public String randomKey() {
        return process(KeyCommand.RANDOMKEY, STRING_ACTION);
    }

    @Override
    public void rename(String key, String newKey) {
        Checks.checkNotBlank(key, "the key for 'rename' is blank!");
        Checks.checkNotBlank(newKey, "the newKey for 'rename' is blank!");
        process(KeyCommand.RENAME, OK_ACTION, key, newKey);
    }

    @Override
    public boolean renamenx(String key, String newKey) {
        Checks.checkNotBlank(key, "the key for 'renamenx' is blank!");
        Checks.checkNotBlank(newKey, "the newKey for 'renamenx' is blank!");
        return process(KeyCommand.RENAMENX, BOOLEAN_ACTION, key, newKey);
    }

    @Override
    public void restore(String key, int ttl, byte[] serializedValue) {
        Checks.checkNotBlank(key, "the key for 'restore' is blank!");
        Checks.checkTrue(serializedValue != null && serializedValue.length > 0, "the serializedValue for 'restore' is empty!");
        /*
         * we can't use process(...) here to avoid data loss when serializedValue turn to String.
         */
        List<Data> elements = new ArrayList<>(4);
        elements.add(new BulkStringsData(KeyCommand.RESTORE.values()[0]));
        elements.add(new BulkStringsData(key));
        elements.add(new BulkStringsData(Integer.toString(ttl)));
        elements.add(new BulkStringsData(serializedValue));
        ArraysData request = new ArraysData(elements);
        if(!client.isRunning()){
            throw new JredicException("the client for network is stopped!");
        }
        Data response = client.send(request);
        DataType dataType = response.getType();
        try{
            if(DataType.SIMPLE_STRINGS.equals(response.getType())){
                SimpleStringsData simpleStringsData = (SimpleStringsData) response;
                if("OK".equals(simpleStringsData.getContent())){
                    return;
                }else{
                    throw new JredicException(simpleStringsData.getContent());
                }
            }
            throw ACTION_EXCEPTION;
        }catch (JredicException e){
            if(ACTION_EXCEPTION == e){
                if(DataType.ERRORS.equals(dataType)){
                    throw new JredicException(((ErrorsData) response).getErrorMsg());
                }else{
                    throw new DataTypeNotSupportException(dataType, KeyCommand.RESTORE);
                }
            }else{
                throw e;
            }
        }
    }

    @Override
    public List<String> sort(String key, SortOptionBuilder optionBuilder) {
        Checks.checkNotBlank(key, "the key for 'sort' is blank!");
        Checks.checkNotNull(optionBuilder, "the optionBuilder for 'sort' is null!");
        return process(KeyCommand.SORT, ARRAY_ACTION, packageParams(optionBuilder.build().toArray(new String[]{}), key));
    }

    @Override
    public long sortAndStore(String key, SortOptionBuilder optionBuilder, String destKey) {
        Checks.checkNotBlank(key, "the key for 'sortAndStore' is blank!");
        Checks.checkNotNull(optionBuilder, "the optionBuilder for 'sortAndStore' is null!");
        Checks.checkNotBlank(destKey, "the destKey for 'sortAndStore' is blank!");
        List<String> options = optionBuilder.build();
        options.add(SortOptionBuilder.OPTION_STORE);
        options.add(destKey);
        return process(KeyCommand.SORT, LONG_ACTION, packageParams(options.toArray(new String[]{}), key));
    }

    @Override
    public RedisDataType type(String key) {
        Checks.checkNotBlank(key, "the key for 'type' is blank!");
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

    @Override
    public long append(String key, String value) {
        Checks.checkNotBlank(key, "the key for 'append' is blank!");
        Checks.checkNotBlank(value, "the value for 'append' is blank!");
        return process(StringCommand.APPEND, LONG_ACTION, key, value);
    }

    @Override
    public long bitCount(String key) {
        Checks.checkNotBlank(key, "the key for 'bitCount' is blank!");
        return process(StringCommand.BITCOUNT, LONG_ACTION, key);
    }

    @Override
    public long bitCount(String key, int start, int end) {
        Checks.checkNotBlank(key, "the key for 'bitCount' is blank!");
        return process(StringCommand.BITCOUNT, LONG_ACTION, key, Integer.toString(start), Integer.toString(end));
    }

    @Override
    public long bitOp(BitOP op, String destKey, String ... srcKeys) {
        Checks.checkNotNull(op, "the op for 'bitOp' is null!");
        Checks.checkNotBlank(destKey, "the destKey for 'bitOp' is blank!");
        Checks.checkArrayNotEmpty(srcKeys, "the srcKeys for 'bitOp' is empty!");
        String[] args = new String[srcKeys.length + 2];
        int index = 0;
        args[index++] = op.name();
        args[index++] = destKey;
        for(String srcKey : srcKeys){
            args[index++] = srcKey;
        }
        return process(StringCommand.BITOP, LONG_ACTION, args);
    }

    @Override
    public long bitPos(String key, Bit bit) {
        Checks.checkNotBlank(key, "the key for 'bitPos' is blank!");
        Checks.checkNotNull(key, "the bit for 'bitPos' is null!");
        return process(StringCommand.BITPOS, LONG_ACTION, key, Integer.toString(bit.value()));
    }

    @Override
    public long bitPos(String key, Bit bit, int start) {
        Checks.checkNotBlank(key, "the key for 'bitPos' is blank!");
        Checks.checkNotNull(key, "the bit for 'bitPos' is null!");
        return process(StringCommand.BITPOS, LONG_ACTION, key, Integer.toString(bit.value()), Integer.toString(start));
    }

    @Override
    public long bitPos(String key, Bit bit, int start, int end) {
        Checks.checkNotBlank(key, "the key for 'bitPos' is blank!");
        Checks.checkNotNull(key, "the bit for 'bitPos' is null!");
        return process(StringCommand.BITPOS, LONG_ACTION, key, Integer.toString(bit.value()), Integer.toString(start), Integer.toString(end));
    }

    /*
     * the last arg in method process(...) is 'String ... args',
     * and in some case, our read args is like 'String k1, String[] ks',
     * so we need this method to do some adaptation.
     */
    private static String[] packageParams(String[] lastParams, String ... firstParams){
        String[] params = new String[lastParams.length + firstParams.length];
        int index = 0;
        for(String param : firstParams){
            params[index++] = param;
        }
        for(String param : lastParams){
            params[index++] = param;
        }
        return params;
    }


    //common process
    private <R> R process(Command cmd, Action<R> action, String ... args){
        //create the request with cmd and args.
        ArraysData request = Commands.createRequest(cmd, args);
        //test if client is running here!
        if(!client.isRunning()){
            throw new JredicException("the client for network is stopped!");
        }
        Data response = client.send(request);
        DataType dataType = response.getType();
        try{
            //do callback
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

    /**
     * The CallBack Interface to dealing Redis Response Data.
     *
     * @param <R>
     */
    private interface Action<R>{

        R doAction(Data data) throws JredicException;

    }

    /*
     * some common callback implementations.
     */
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
                return ((BulkStringsData) data).getStringContent();
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

    /*
     * Some command return 'OK' like SET,
     * and if we pass it to the user, it will be like this:
     *
     * if("OK".equals(jredic.set("mykey","myvalue"))){
     *      //do some thing...
     * }else{
     *      //throws exception
     * }
     *
     * i don't think it's a good iead.
     * i prefer to difine the SET method like this:
     *
     * public void set(String key, String value);
     *
     * when user using this method, they just do this:
     * jredic.set("mykey", myvalue);
     *
     * and if something err occur, just catch it.
     */
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
                        keyList.add(((BulkStringsData)element).getStringContent());
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

    /**
     * init resources.
     *
     * @throws JredicException
     */
    public void init() throws JredicException{

        try{
            this.client.start();
        } catch (Exception e){
            throw new JredicException("DefaultJredic init failed!", e);
        }

    }

    /**
     * destroy resources.
     *
     * @throws JredicException
     */
    public void destroy() throws JredicException{

        try{
            this.client.stop();
        } catch (Exception e){
            throw new JredicException("DefaultJredic destroy failed!", e);
        }

    }

    @Override
    public String toString() {
        return "DefaultJredic{" +
                "client=" + client +
                '}';
    }
}
