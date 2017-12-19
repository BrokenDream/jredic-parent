package com.jredic;

import com.jredic.exception.IllegalParameterException;
import com.jredic.exception.JredicException;

import java.util.List;
import java.util.Map;

/**
 * Define operations for 'Hash' commands.
 * See {@link com.jredic.command.HashCommand}
 *
 * @author David.W
 */
public interface HashClient {

    /**
     * Removes the specified fields from the hash stored at key.
     * If key does not exist, it is treated as an empty hash.
     *
     * @param key the key associated to hash to operate.
     * @param field the field to remove.
     * @return
     *      if field were removed from hash,return true;
     *      if field does not exist,return false.
     * @throws IllegalParameterException if key is blank OR field is blank.
     * @throws JredicException if some other err occur.
     */
    boolean hdel(String key, String field);

    /**
     * Removes the specified fields from the hash stored at key.
     * Specified fields that do not exist within this hash are ignored.
     *
     * @since Redis Version 2.4 accepts multiple fields.
     * @param key the key associated to hash to operate.
     * @param fields the fields to remove.
     * @return
     *      the number of fields that were removed from the hash,
     *      not including specified but non existing fields.
     * @throws IllegalParameterException if key is blank OR fields is empty.
     * @throws JredicException if some other err occur.
     */
    long hdel(String key, String ... fields);

    /**
     * Returns if field is an existing field in the hash stored at key.
     *
     * @param key the key associated to hash to operate.
     * @param field the field in the hash.
     * @return
     *      if the hash contains field,return true;
     *      if the hash does not contain field or key does not exist,return false.
     * @throws IllegalParameterException if key is blank OR field is blank.
     * @throws JredicException if some other err occur.
     */
    boolean hexists(String key, String field);

    /**
     * Returns all fields and values of the hash stored at key.
     *
     * @param key the key associated to hash to operate.
     * @return
     *      a map of fields and their values stored in the hash,
     *      or null when key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    Map<String, String> hgetAll(String key);

    /**
     * Increments the number stored at field in the hash stored at key by increment.
     * If key does not exist, a new key holding a hash is created.
     * If field does not exist the value is set to 0 before the operation is performed.
     *
     * @param key the key associated to hash to operate.
     * @param field the field in the hash to increment.
     * @param increment increment value.
     * @return
     *      the value at field after the increment operation.
     * @throws IllegalParameterException if key is blank OR field is blank.
     * @throws JredicException if some other err occur.
     */
    long hincrBy(String key, String field, long increment);

    /**
     * Increment the specified field of a hash stored at key,
     * and representing a floating point number, by the specified increment.
     * If the increment value is negative,
     * the result is to have the hash field value decremented instead of incremented.
     * If the field does not exist, it is set to 0 before performing the operation.
     *
     * @param key the key associated to hash to operate.
     * @param field the field in the hash to increment.
     * @param increment increment value.
     * @return
     *      the value at field after the increment operation.
     * @throws IllegalParameterException if key is blank OR field is blank.
     * @throws JredicException if some other err occur.
     */
    double hincrByFloat(String key, String field, double increment);

    /**
     * Returns all field names in the hash stored at key.
     *
     * @param key the key associated to hash to operate.
     * @return
     *      list of fields in the hash,
     *      or null when key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    List<String> hkeys(String key);

    /**
     * Returns the number of fields contained in the hash stored at key.
     *
     * @param key the key associated to hash to operate.
     * @return
     *      number of fields in the hash, or 0 when key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long hlen(String key);

    /**
     * Returns the values associated with the specified fields in the hash stored at key.
     * For every field that does not exist in the hash, a nil value is returned.
     *
     * @param key the key associated to hash to operate.
     * @param fields the fields to get.
     * @return
     *      list of values associated with the given fields, in the same order as they are requested.
     * @throws IllegalParameterException if key is blank OR fields is empty.
     * @throws JredicException if some other err occur.
     */
    List<String> hmget(String key, String ... fields);

    /**
     * Sets the specified fields to their respective values in the hash stored at key.
     * This method overwrites any specified fields already existing in the hash.
     * If key does not exist, a new key holding a hash is created.
     *
     * @param key the key associated to hash to operate.
     * @param pairs the field-values to set.
     * @throws IllegalParameterException if key is blank OR pairs is empty.
     * @throws JredicException if some other err occur.
     */
    void hmset(String key, Map<String, String> pairs);

    /**
     * Sets field in the hash stored at key to value.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists in the hash, it is overwritten.
     *
     * @param key the key associated to hash to operate.
     * @param field the field to set.
     * @param value value.
     * @return
     *      if field is a new field in the hash and value was set, return true;
     *      if field already exists in the hash and the value was updated, return false.
     * @throws IllegalParameterException if key is blank OR field is blank OR value is blank .
     * @throws JredicException if some other err occur.
     */
    boolean hset(String key, String field, String value);

    /**
     * Sets field in the hash stored at key to value, only if field does not yet exist.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists, this operation has no effect.
     *
     * @param key the key associated to hash to operate.
     * @param field the field to set.
     * @param value value.
     * @return
     *      if field is a new field in the hash and value was set, return true;
     *      if field already exists in the hash and no operation was performed, return false.
     * @throws IllegalParameterException if key is blank OR field is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    boolean hsetnx(String key, String field, String value);

    /**
     * Returns the string length of the value associated with field in the hash stored at key.
     * If the key or the field do not exist, 0 is returned.
     *
     * @param key the key associated to hash to operate.
     * @param field the field to get value's length..
     * @return
     *      the string length of the value associated with field,
     *      or 0 when field is not present in the hash or key does not exist at all.
     * @throws IllegalParameterException if key is blank OR field is blank.
     * @throws JredicException if some other err occur.
     */
    long hstrLen(String key, String field);

    /**
     * Returns all values in the hash stored at key.
     *
     * @param key the key associated to hash to operate.
     * @return
     *      list of values in the hash, or null when key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    List<String> hvals(String key);

}
