package com.jredic.support;

import com.jredic.Jredic;
import com.jredic.RedisDataType;
import com.jredic.command.*;
import com.jredic.command.sub.*;
import com.jredic.exception.DataTypeNotSupportException;
import com.jredic.exception.JredicException;
import com.jredic.network.client.Client;
import com.jredic.network.protocol.data.*;
import com.jredic.util.Checks;
import com.jredic.util.Strings;
import java.util.*;

/**
 * The default implementation for Jredic.
 *
 * @author David.W
 */
public class DefaultJredic implements Jredic {

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
                if(SIMPLE_DATA_SUCCESS.equals(simpleStringsData.getContent())){
                    return;
                }else{
                    throw new JredicException(simpleStringsData.getContent());
                }
            }
            throw ACTION_EXCEPTION;
        }catch (JredicException e){
            if(ACTION_EXCEPTION == e){
                if(DataType.ERRORS.equals(dataType)){
                    Command cmd = KeyCommand.RESTORE;
                    StringBuilder errorMessageBuilder = new StringBuilder();
                    errorMessageBuilder.append("execute command ");
                    errorMessageBuilder.append(Arrays.toString(cmd.values()));
                    errorMessageBuilder.append(" ");
                    if(!Strings.isNullOrEmpty(cmd.addition())){
                        errorMessageBuilder.append("(");
                        errorMessageBuilder.append(cmd.addition());
                        errorMessageBuilder.append(") ");
                    }
                    errorMessageBuilder.append("which start with the redis version [");
                    errorMessageBuilder.append(cmd.startVersion());
                    errorMessageBuilder.append("] failed! reason:");
                    errorMessageBuilder.append(((ErrorsData) response).getErrorMsg());
                    throw new JredicException(errorMessageBuilder.toString());
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

    @Override
    public long decr(String key) {
        Checks.checkNotBlank(key, "the key for 'decr' is blank!");
        return process(StringCommand.DECR, LONG_ACTION, key);
    }

    @Override
    public long decrBy(String key, long decrement) {
        Checks.checkNotBlank(key, "the key for 'decrBy' is blank!");
        return process(StringCommand.DECRBY, LONG_ACTION, key, Long.toString(decrement));
    }

    @Override
    public String get(String key) {
        Checks.checkNotBlank(key, "the key for 'get' is blank!");
        return process(StringCommand.GET, STRING_ACTION, key);
    }

    @Override
    public long getBit(String key, int offset) {
        Checks.checkNotBlank(key, "the key for 'getBit' is blank!");
        Checks.checkTrue(offset >= 0, "the offset of 'getBit' is negative!");
        return process(StringCommand.GETBIT, LONG_ACTION, key, Integer.toString(offset));
    }

    @Override
    public String getRange(String key, int start, int end) {
        Checks.checkNotBlank(key, "the key for 'getRange' is blank!");
        return process(StringCommand.GETRANGE, STRING_ACTION, key, Integer.toString(start), Integer.toString(end));
    }

    @Override
    public String getSet(String key, String value) {
        Checks.checkNotBlank(key, "the key for 'getSet' is blank!");
        Checks.checkNotBlank(value, "the value for 'getSet' is blank!");
        return process(StringCommand.GETSET, STRING_ACTION, key, value);
    }

    @Override
    public long incr(String key) {
        Checks.checkNotBlank(key, "the key for 'incr' is blank!");
        return process(StringCommand.INCR, LONG_ACTION, key);
    }

    @Override
    public long incrBy(String key, long increment) {
        Checks.checkNotBlank(key, "the key for 'incrBy' is blank!");
        return process(StringCommand.INCRBY, LONG_ACTION, key);
    }

    @Override
    public double incrByFloat(String key, double increment) {
        Checks.checkNotBlank(key, "the key for 'incrByFloat' is blank!");
        String value = process(StringCommand.INCRBYFLOAT, STRING_ACTION, key, Double.toString(increment));
        return Double.parseDouble(value);
    }

    @Override
    public List<String> mget(String... keys) {
        Checks.checkArrayNotEmpty(keys, "the keys for 'mget' is empty!");
        return process(StringCommand.MGET, ARRAY_ACTION, keys);
    }

    @Override
    public void mset(Map<String, String> pairs) {
        Checks.checkTrue(pairs != null && pairs.size() > 0, "the keys for 'mset' is empty!");
        String[] params = new String[pairs.size() * 2];
        int index = 0;
        for(String key : pairs.keySet()){
            params[index++] = key;
            params[index++] = pairs.get(key);
        }
        process(StringCommand.MSET, OK_ACTION, params);
    }

    @Override
    public boolean msetnx(Map<String, String> pairs) {
        Checks.checkTrue(pairs != null && pairs.size() > 0, "the pairs for 'msetnx' is empty!");
        String[] params = new String[pairs.size() * 2];
        int index = 0;
        for(String key : pairs.keySet()){
            params[index++] = key;
            params[index++] = pairs.get(key);
        }
        return process(StringCommand.MSETNX, BOOLEAN_ACTION, params);
    }

    @Override
    public void psetex(String key, long millis, String value) {
        Checks.checkNotBlank(key, "the key for 'psetex' is blank!");
        Checks.checkNotBlank(value, "the value for 'psetex' is blank!");
        process(StringCommand.PSETEX, OK_ACTION, key, Long.toString(millis), value);
    }

    @Override
    public void set(String key, String value) {
        Checks.checkNotBlank(key, "the key for 'set' is blank!");
        Checks.checkNotBlank(value, "the value for 'set' is blank!");
        process(StringCommand.SET, OK_ACTION, key, value);
    }

    @Override
    public void set(String key, String value, SetOptionBuilder optionBuilder) {
        set(key, value, optionBuilder, null);
    }

    @Override
    public void set(String key, String value, SetOptionBuilder optionBuilder, final ConditionMeetListener meetListener) {
        Checks.checkNotBlank(key, "the key for 'set' is blank!");
        Checks.checkNotBlank(value, "the value for 'set' is blank!");
        Checks.checkNotNull(optionBuilder, "the optionBuilder for 'set' is null!");
        process(KeyCommand.SORT, new Action<Void>() {
            /*
             * if the options nx/xx not met, it will return null.
             */
            @Override
            public Void doAction(Data data) throws JredicException {
                if(DataType.SIMPLE_STRINGS.equals(data.getType())){
                    SimpleStringsData simpleStringsData = (SimpleStringsData) data;
                    if(SIMPLE_DATA_SUCCESS.equals(simpleStringsData.getContent())){
                        if(meetListener != null){
                            meetListener.onMeet(true);
                        }
                        return null;
                    }else{
                        if(simpleStringsData.getContent() == null){
                            if(meetListener != null){
                                meetListener.onMeet(false);
                            }
                            return null;
                        }
                        throw new JredicException(simpleStringsData.getContent());
                    }
                }
                throw ACTION_EXCEPTION;
            }
        }, packageParams(optionBuilder.build().toArray(new String[]{}), key));
    }

    @Override
    public long setBit(String key, int offset, Bit bit) {
        Checks.checkNotBlank(key, "the key for 'setBit' is blank!");
        Checks.checkNotNull(bit, "the bit for 'setBit' is null!");
        return process(StringCommand.SETBIT, LONG_ACTION, key, Integer.toString(offset), Integer.toString(bit.value()));
    }

    @Override
    public void setex(String key, int seconds, String value) {
        Checks.checkNotBlank(key, "the key for 'setex' is blank!");
        Checks.checkNotBlank(value, "the value for 'setex' is blank!");
        process(StringCommand.SETEX, OK_ACTION, key, Integer.toString(seconds), value);
    }

    @Override
    public long setRange(String key, int offset, String value) {
        Checks.checkNotBlank(key, "the key for 'setRange' is blank!");
        Checks.checkNotBlank(value, "the value for 'setRange' is blank!");
        return process(StringCommand.SETRANGE, LONG_ACTION, key, Integer.toString(offset), value);
    }

    @Override
    public long strLen(String key) {
        Checks.checkNotBlank(key, "the key for 'strLen' is blank!");
        return process(StringCommand.STRLEN, LONG_ACTION, key);
    }

    @Override
    public boolean hdel(String key, String field) {
        Checks.checkNotBlank(key, "the key for 'hdel' is blank!");
        Checks.checkNotBlank(field, "the field for 'hdel' is blank!");
        return process(HashCommand.HDEL, BOOLEAN_ACTION, key, field);
    }

    @Override
    public long hdel(String key, String... fields) {
        Checks.checkNotBlank(key, "the key for 'hdel' is blank!");
        Checks.checkArrayNotEmpty(fields, "the fields for 'hdel' is empty!");
        return process(HashCommand.HDEL_MU, LONG_ACTION, packageParams(fields, key));
    }

    @Override
    public boolean hexists(String key, String field) {
        Checks.checkNotBlank(key, "the key for 'hexists' is blank!");
        Checks.checkNotBlank(field, "the field for 'hexists' is blank!");
        return process(HashCommand.HEXISTS, BOOLEAN_ACTION, key, field);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Checks.checkNotBlank(key, "the key for 'hgetAll' is blank!");
        return process(HashCommand.HGETALL, HASH_ACTION, key);
    }

    @Override
    public long hincrBy(String key, String field, long increment) {
        Checks.checkNotBlank(key, "the key for 'hincrBy' is blank!");
        Checks.checkNotBlank(field, "the field for 'hincrBy' is blank!");
        return process(HashCommand.HINCRBY, LONG_ACTION, key, field, Long.toString(increment));
    }

    @Override
    public double hincrByFloat(String key, String field, double increment) {
        Checks.checkNotBlank(key, "the key for 'hincrByFloat' is blank!");
        Checks.checkNotBlank(field, "the field for 'hincrByFloat' is blank!");
        String value = process(HashCommand.HINCRBY, STRING_ACTION, key, field, Double.toString(increment));
        return Double.parseDouble(value);
    }

    @Override
    public List<String> hkeys(String key) {
        Checks.checkNotBlank(key, "the key for 'hkeys' is blank!");
        return process(HashCommand.HKEYS, ARRAY_ACTION, key);
    }

    @Override
    public long hlen(String key) {
        Checks.checkNotBlank(key, "the key for 'hlen' is blank!");
        return process(HashCommand.HLEN, LONG_ACTION, key);
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Checks.checkNotBlank(key, "the key for 'hmget' is blank!");
        Checks.checkArrayNotEmpty(fields, "the fields for 'hmget' is empty!");
        return process(HashCommand.HMGET, ARRAY_ACTION, packageParams(fields, key));
    }

    @Override
    public void hmset(String key, Map<String, String> pairs) {
        Checks.checkNotBlank(key, "the key for 'hmset' is blank!");
        Checks.checkTrue(pairs != null && pairs.size() > 0, "the pairs for 'hmset' is empty!");
        String[] params = new String[pairs.size() * 2 + 1];
        int index = 0;
        params[index++] = key;
        for(String field : pairs.keySet()){
            params[index++] = field;
            params[index++] = pairs.get(field);
        }
        process(HashCommand.HMSET, OK_ACTION, params);
    }

    @Override
    public boolean hset(String key, String field, String value) {
        Checks.checkNotBlank(key, "the key for 'hset' is blank!");
        Checks.checkNotBlank(field, "the field for 'hset' is blank!");
        Checks.checkNotBlank(value, "the value for 'hset' is blank!");
        return process(HashCommand.HSET, BOOLEAN_ACTION, key, field, value);
    }

    @Override
    public boolean hsetnx(String key, String field, String value) {
        Checks.checkNotBlank(key, "the key for 'hsetnx' is blank!");
        Checks.checkNotBlank(field, "the field for 'hsetnx' is blank!");
        Checks.checkNotBlank(value, "the value for 'hsetnx' is blank!");
        return process(HashCommand.HSETNX, BOOLEAN_ACTION, key, field, value);
    }

    @Override
    public long hstrLen(String key, String field) {
        Checks.checkNotBlank(key, "the key for 'hstrLen' is blank!");
        Checks.checkNotBlank(field, "the field for 'hstrLen' is blank!");
        return process(HashCommand.HSTRLEN, LONG_ACTION, key, field);
    }

    @Override
    public List<String> hvals(String key) {
        Checks.checkNotBlank(key, "the key for 'hvals' is blank!");
        return process(HashCommand.HVALS, ARRAY_ACTION, key);
    }

    @Override
    public void auth(String password) {
        Checks.checkNotBlank(password, "the password for 'auth' is blank!");
        process(ConnectionCommand.AUTH, OK_ACTION, password);
    }

    @Override
    public String echo(String message) {
        Checks.checkNotBlank(message, "the message for 'echo' is blank!");
        return process(ConnectionCommand.ECHO, STRING_ACTION, message);
    }

    @Override
    public String ping() {
        return process(ConnectionCommand.PING, STRING_ACTION);
    }

    @Override
    public String ping(String message) {
        Checks.checkNotBlank(message, "the message for 'ping' is blank!");
        return process(ConnectionCommand.PING, STRING_ACTION, message);
    }

    @Override
    public void quit() {
        process(ConnectionCommand.QUIT, OK_ACTION);
    }

    @Override
    public void select(int index) {
        Checks.checkTrue(index >= 0, "the index for 'select' is negative!");
        process(ConnectionCommand.SELECT, OK_ACTION, Integer.toString(index));
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void init() throws JredicException{

        try{
            this.client.start();
        } catch (Exception e){
            throw new JredicException("DefaultJredic init failed!", e);
        }

    }

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
                    StringBuilder errorMessageBuilder = new StringBuilder();
                    errorMessageBuilder.append("execute command ");
                    errorMessageBuilder.append(Arrays.toString(cmd.values()));
                    errorMessageBuilder.append(" ");
                    if(!Strings.isNullOrEmpty(cmd.addition())){
                        errorMessageBuilder.append("(");
                        errorMessageBuilder.append(cmd.addition());
                        errorMessageBuilder.append(") ");
                    }
                    errorMessageBuilder.append("which start with the redis version [");
                    errorMessageBuilder.append(cmd.startVersion());
                    errorMessageBuilder.append("] failed! reason:");
                    errorMessageBuilder.append(((ErrorsData) response).getErrorMsg());
                    throw new JredicException(errorMessageBuilder.toString());
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
    private static final Action<Void> OK_ACTION = new OkAction();
    private static final Action<Map<String, String>> HASH_ACTION = new HashAction();

    private static final JredicException ACTION_EXCEPTION = new JredicException();

    /*
     * for hash.
     * like hgetall command, in the returned value, every field name is followed by its value as a array.
     */
    private static class HashAction implements Action<Map<String, String>>{

        @Override
        public Map<String, String> doAction(Data data) throws JredicException {
            if(DataType.ARRAYS.equals(data.getType())){
                ArraysData arraysData = (ArraysData) data;
                List<Data> elements = arraysData.getElements();
                if(elements == null || elements.size() == 0){
                    return null;
                }else{
                    Map<String, String> map = new HashMap<>(elements.size() / 2);
                    for(int i=0; i<elements.size() ;i+=2){
                        map.put(((BulkStringsData)elements.get(i)).getStringContent(),
                                ((BulkStringsData)elements.get(i+1)).getStringContent());
                    }
                    return map;
                }
            }
            throw ACTION_EXCEPTION;
        }

    }

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

    private static final String SIMPLE_DATA_SUCCESS = "OK";

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
    private static class OkAction implements Action<Void>{

        @Override
        public Void doAction(Data data) throws JredicException{
            if(DataType.SIMPLE_STRINGS.equals(data.getType())){
                SimpleStringsData simpleStringsData = (SimpleStringsData) data;
                if(SIMPLE_DATA_SUCCESS.equals(simpleStringsData.getContent())){
                    return null;
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


}
