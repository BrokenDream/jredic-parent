package com.jredic;

import com.jredic.command.sub.*;
import com.jredic.exception.JredicException;
import com.jredic.exception.IllegalParameterException;

import java.util.List;

/**
 * The core interface of Java Remote Dictionary Client.
 *
 *
 * @author David.W
 */
public interface Jredic {

    /*
     * ops to manage 'Key'
     */

    /**
     * Removes the specified keys.
     * A key is ignored if it does not exist.
     *
     * @param keys the keys to operate.
     * @return
     *      The number of keys that were removed.
     * @throws IllegalParameterException if keys is empty.
     * @throws JredicException if some other err occur.
     */
    long del(String ... keys);

    /**
     * Removes the specified key.
     *
     * @param key the key to operate.
     * @return
     *      true if key was removed;false if key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean del(String key);

    /**
     * Serialize the value stored at key in a Redis-specific format and return it to the user.
     *
     * @param key the key to operate.
     * @return
     *      the serialized value.If key does not exist, return null.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    byte[] dump(String key);

    /**
     * Test if the specified key exists.
     *
     * @param key the key to test.
     * @return
     *      if the key exists,return true;otherwise false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean exists(String key);

    /**
     * Test if the specified keys exists.
     * @since Redis Server Version:3.0.3.
     *
     * @param firstKey the first key to test.
     * @param otherKeys the other keys to test.
     * @return
     *      The number of keys existing among the ones specified as arguments.
     *      Keys mentioned multiple times and existing are counted multiple times.
     * @throws IllegalParameterException if firstKey is blank OR otherKeys is empty.
     * @throws JredicException if some other err occur.
     */
    long exists(String firstKey, String ... otherKeys);

    /**
     * Set a timeout on key. After the timeout has expired, the key will automatically be deleted.
     *
     * @param key the key to operate
     * @param seconds the expire time in seconds
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean expire(String key, int seconds);

    /**
     * EXPIREAT has the same effect and semantic as EXPIRE, but instead of specifying the number of
     * seconds representing the TTL (time to live), it takes an absolute Unix timestamp
     * (seconds since January 1, 1970). A timestamp in the past will delete the key immediately.
     *
     * @param key the key to operate
     * @param unixTimeInSeconds the time when the key expire
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean expireAt(String key, long unixTimeInSeconds);

    /**
     * This method works exactly like {@link #expire(String, int)}.
     * but the time to live of the key is specified in milliseconds instead of seconds.
     *
     * @param key the key to operate
     * @param millis the expire time in milliseconds
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean pexpire(String key, long millis);

    /**
     * This method works exactly like {@link #expireAt(String, long)}.
     * but the Unix time at which the key will expire is specified in milliseconds instead of seconds.
     *
     * @param key the key to operate
     * @param unixTimeInMillis the time when the key expire
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean pexpireAt(String key, long unixTimeInMillis);

    /**
     * Returns all keys matching pattern.
     * <p>
     * Supported glob-style patterns:
     * <li>
     *     h?llo matches hello, hallo and hxllo
     * </li>
     * <li>
     *     h*llo matches hllo and heeeello
     * </li>
     * <li>
     *     h[ae]llo matches hello and hallo, but not hillo
     * </li>
     * <li>
     *     h[^e]llo matches hallo, hbllo, ... but not hello
     * </li>
     * <li>
     *     h[a-b]llo matches hallo and hbllo
     * </li>
     * <p>
     * Use '\' to escape special characters if you want to match them verbatim.
     *
     * @param pattern the key pattern.
     * @return
     *      list of keys matching pattern;if no matched key,return null;
     * @throws IllegalParameterException if pattern is blank.
     * @throws JredicException if some other err occur.
     */
    List<String> keys(String pattern);

    /**
     * Move key from the currently selected database to the specified destination database.
     * When key already exists in the destination database,
     * or it does not exist in the source database,it does nothing.
     * It is possible to use MOVE as a locking primitive because of this.
     *
     * @param key the key to operate
     * @param dbIndex the index of dest db.
     * @return
     *      if key was moved,return true;otherwise return false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean move(String key, int dbIndex);

    /**
     * Remove the existing timeout on key, turning the key from volatile (a key with an expire set)
     * to persistent (a key that will never expire as no timeout is associated).
     *
     * @param key the key to operate.
     * @return
     *      if the timeout was removed,return true;
     *      if key does not exist or does not have an associated timeout,return false.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    boolean persist(String key);

    /**
     * Returns the remaining time to live of a key that has a timeout.
     * <p>
     * This introspection capability allows a Redis client to check how many seconds a given key will
     * continue to be part of the dataset.
     * <p>
     * In Redis 2.6 or older the command returns -1 if the key does not exist
     * or if the key exist but has no associated expire.
     * <p>
     * Starting with Redis 2.8 the return value in case of error changed:
     * <li>
     *     The command returns -2 if the key does not exist.
     * </li>
     * <li>
     *     The command returns -1 if the key exists but has no associated expire.
     * </li>
     *
     * @param key the key to get ttl.
     * @return
     *      TTL in seconds, or a negative value in order to signal an error.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long ttl(String key);

    /**
     * Like {@link #ttl(String)} this method returns the remaining time to live of a key
     * that has an expire set, with the sole difference that {@link #ttl(String)} returns
     * the amount of remaining time in seconds while {@link #pttl(String)} returns it in milliseconds.
     * <p>
     * In Redis 2.6 or older the command returns -1 if the key does not exist
     * or if the key exist but has no associated expire.
     * <p>
     * Starting with Redis 2.8 the return value in case of error changed:
     * <li>
     *     The command returns -2 if the key does not exist.
     * </li>
     * <li>
     *     The command returns -1 if the key exists but has no associated expire.
     * </li>
     *
     * @param key the key to get ttl in millis..
     * @return
     *      TTL in milliseconds, or a negative value in order to signal an error.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long pttl(String key);

    /**
     * Return a random key from the currently selected database.
     *
     * @return
     *      the random key, or null when the database is empty.
     * @throws JredicException if some err occur.
     */
    String randomKey();

    /**
     * Renames key to newkey.
     *
     * @param key the key to operate.
     * @param newKey the new name.
     * @throws IllegalParameterException if key is blank OR newKey is blank.
     * @throws JredicException if key does not exist; if key and newKey are same(Before Redis 3.2.0);
     */
    void rename(String key, String newKey);

    /**
     * Renames key to newkey if newkey does not yet exist.
     *
     * @param key the key to operate.
     * @param newKey the new name.
     * @return
     *      true if key was renamed to newKey;false if newKey already exists.
     * @throws IllegalParameterException if key is blank OR newKey is blank.
     * @throws JredicException if key does not exist; if key and newKey are same(Before Redis 3.2.0);
     */
    boolean renamenx(String key, String newKey);

    /**
     * Create a key associated with a value that is obtained by deserializing
     * the provided serialized value (obtained via DUMP).If ttl is 0 the key
     * is created without any expire, otherwise the specified expire time (in milliseconds) is set.
     *
     * @param key the key to operate.
     * @param ttl expire time in milliseconds.
     * @param serializedValue serialized value associate to the key.
     * @throws IllegalParameterException if key is blank OR serializedValue is empty.
     * @throws JredicException if key already exists(Redis 3.0 or greater),or some other error occur.
     */
    void restore(String key, int ttl, byte[] serializedValue);

    /**
     * Sort the elements contained in the list, set or sorted set at key.
     * <p>
     * This method doesn't sort the original data, just return the sorted data.
     *
     * @param key the key to operate.
     * @param optionBuilder the builder for sort options.
     * @return
     *      the elements after sorting.
     * @throws IllegalParameterException if key is blank OR optionBuilder is null.
     * @throws JredicException if some other err occur.
     */
    List<String> sort(String key, SortOptionBuilder optionBuilder);

    /**
     * Sort the elements contained in the list, set or sorted set at key.
     * <p>
     * This method doesn't sort the original data, just return the sorted data.
     *
     * @param key the key to operate.
     * @param optionBuilder the builder for sort options.
     * @param destKey the dest key to store sorted elements.
     * @return
     *      the number of sorted elements in the destination list.
     * @throws IllegalParameterException if key is blank OR optionBuilder is null OR destKey is blank.
     * @throws JredicException if some other err occur.
     */
    long sortAndStore(String key, SortOptionBuilder optionBuilder, String destKey);

    /**
     * Returns the type of the value stored at key.
     * @see RedisDataType
     *
     * @param key the key to get type.
     * @return
     *      the type of value at key;null if key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    RedisDataType type(String key);


    /*
     * ops to manage 'String'
     */

    /**
     * If key already exists and is a string, this method appends the value at the end of the string.
     * If key does not exist it is created and set as an empty string, so it will be similar
     * to {@link #set(String, String)} in this special case.
     *
     * @param key the key to operate.
     * @param value appended value.
     * @return
     *      the length of the string after the append.
     * @throws IllegalParameterException if key is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    long append(String key, String value);

    /**
     * Count the number of set bits (population counting) in a string.
     *
     * @param key the key to count bit.
     * @return
     *      The number of bits set to 1.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long bitCount(String key);

    /**
     * Count the number of set bits (population counting) in a string from start to end.
     *
     * @param key the key to count bit.
     * @param start start index.
     * @param end end index.
     * @return
     *      The number of bits set to 1.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long bitCount(String key, int start, int end);

    /**
     * Perform a bitwise operation between multiple keys (containing string values)
     * and store the result in the destination key.
     *
     * <p>
     * Note if op is {@link BitOP#NOT}, this method will only take one srcKeys.
     *
     * <p>
     * <b>Handling of strings with different lengths</b>
     * When an operation is performed between strings having different lengths,
     * all the strings shorter than the longest string in the set are treated
     * as if they were zero-padded up to the length of the longest string.
     * The same holds true for non-existent keys, that are considered
     * as a stream of zero bytes up to the length of the longest string.
     *
     * @param op the operation see {@link BitOP}
     * @param destKey the dest key to store result.
     * @param srcKeys the src keys to operate.
     * @return
     *      The size of the string stored in the destination key,
     *      that is equal to the size of the longest input string.
     * @throws IllegalParameterException if op is null OR destKey is blank OR srcKeys is empty.
     * @throws JredicException if some other err occur.
     */
    long bitOp(BitOP op, String destKey, String ... srcKeys);

    /**
     * Return the position of the first bit set to 1 or 0 in a string.
     * <p>
     * The position is returned, thinking of the string as an array of bits from left to right,
     * where the first byte's most significant bit is at position 0,
     * the second byte's most significant bit is at position 8, and so forth.
     *
     * @param key the key to get position of 0 or 1.
     * @param bit set(1) or clear(0), see {@link Bit}.
     * @return
     *      the position of the first bit set to 1 or 0 according to the request.
     * @throws IllegalParameterException if key is blank OR bit is null.
     * @throws JredicException if some other err occur.
     */
    long bitPos(String key, Bit bit);

    /**
     * Return the position of the first bit set to 1 or 0 in a string.
     * <p>
     * The position is returned, thinking of the string as an array of bits from left to right,
     * where the first byte's most significant bit is at position 0,
     * the second byte's most significant bit is at position 8, and so forth.
     *
     * @param key the key to get position of 0 or 1.
     * @param bit set(1) or clear(0), see {@link Bit}.
     * @param start the start index (in byte).
     * @return
     *      the position of the first bit set to 1 or 0 according to the request.
     * @throws IllegalParameterException if key is blank OR bit is null.
     * @throws JredicException if some other err occur.
     */
    long bitPos(String key, Bit bit, int start);

    /**
     * Return the position of the first bit set to 1 or 0 in a string.
     * <p>
     * The position is returned, thinking of the string as an array of bits from left to right,
     * where the first byte's most significant bit is at position 0,
     * the second byte's most significant bit is at position 8, and so forth.
     *
     * @param key the key to get position of 0 or 1.
     * @param bit set(1) or clear(0), see {@link Bit}.
     * @param start the start index (in byte).
     * @param end the end index (in byte).
     * @return
     *      the position of the first bit set to 1 or 0 according to the request.
     * @throws IllegalParameterException if key is blank OR bit is null.
     * @throws JredicException if some other err occur.
     */
    long bitPos(String key, Bit bit, int start, int end);

    /**
     * Decrements the number stored at key by one.
     * <p>
     * If the key does not exist, it is set to 0 before performing the operation.
     * <p>
     * An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer.
     *
     * @param key the key to operate.
     * @return
     *      the value of key after the decrement.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long decr(String key);

    /**
     * Decrements the number stored at key by decrement.
     * <p>
     * If the key does not exist, it is set to 0 before performing the operation.
     * <p>
     * An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer.
     *
     * @param key the key to operate.
     * @param decrement decrement value.
     * @return
     *      the value of key after the decrement.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long decrBy(String key, long decrement);

    /**
     * Get the value of key.
     *
     * @param key the key to get value.
     * @return
     *      the value of key, or null when key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    String get(String key);

    /**
     * Returns the bit value at offset in the string value stored at key.
     * <p>
     * When offset is beyond the string length, the string is assumed to be a contiguous space with 0 bits.
     * <p>
     * When key does not exist it is assumed to be an empty string,
     * so offset is always out of range and the value is also assumed to be a contiguous space with 0 bits.
     *
     * @param key the key to get bit.
     * @param offset the offset where bit store.
     * @return
     *      the bit value (0 or 1) store in the offset of the key.
     * @throws IllegalParameterException if key is blank OR offset is negative.
     * @throws JredicException if some other err occur.
     */
    long getBit(String key, int offset);

    /**
     * Returns the substring of the string value stored at key, determined by the offsets start and end (both are inclusive).
     * <p>
     * Negative offsets can be used in order to provide an offset starting from the end of the string.
     * So -1 means the last character, -2 the penultimate and so forth.
     *
     * @param key the key to getRange.
     * @param start the start offset.
     * @param end the end offset.
     * @return
     *      the substring from start to end.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    String getRange(String key, int start, int end);

    /**
     * <b>Atomically</> sets key to value and returns the old value stored at key.
     * Returns an error when key exists but does not hold a string value.
     *
     * @param key the key to operate.
     * @param value the value to set.
     * @return
     *      the old value stored at key, or null when key did not exist.
     * @throws IllegalParameterException if key is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    String getSet(String key, String value);

    /**
     * Increments the number stored at key by one.
     * <p>
     * If the key does not exist, it is set to 0 before performing the operation.
     * <p>
     * An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer.
     *
     * @param key the key to operate.
     * @return
     *      the value of key after the increment.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long incr(String key);

    /**
     * Increments the number stored at key by increment.
     * <p>
     * If the key does not exist, it is set to 0 before performing the operation.
     * <p>
     * An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer.
     *
     * @param key the key to operate.
     * @param increment increment value.
     * @return
     *      the value of key after the increment.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long incrBy(String key, long increment);

    /**
     * Increment the string representing a floating point number stored at key by the specified increment.
     * <p>
     * By using a negative increment value, the result is that the value stored at the key is decremented (by the obvious properties of addition).
     * <p>
     * If the key does not exist, it is set to 0 before performing the operation.
     * <p>
     * An error is returned if one of the following conditions occur:
     * <li>
     *     The key contains a value of the wrong type (not a string).
     * </li>
     * <li>
     *     The current key content or the specified increment are not parsable as a double precision floating point number.
     * </li>
     *
     * @param key the key to operate.
     * @param increment increment value.
     * @return
     *      the value of key after the increment.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    double incrByFloat(String key, double increment);

    /**
     * Returns the values of all specified keys.
     * For every key that does not hold a string value or does not exist,
     * the special value nil is returned. Because of this, the operation never fails.
     *
     * @param keys the keys to get.
     * @return
     *      list of values at the specified keys.
     * @throws IllegalParameterException if keys is empty.
     * @throws JredicException if some other err occur.
     */
    List<String> mget(String ... keys);

    /**
     * Sets the given keys to their respective values.
     * <p>
     * this method is atomic, so all given keys are set at once.
     * It is not possible for clients to see that some of the keys were updated while others are unchanged.
     *
     * @param pairs the pairs to set.
     * @throws IllegalParameterException if pairs is empty.
     * @throws JredicException if some other err occur.
     */
    void mset(Pair ... pairs);

    /**
     * Sets the given keys to their respective values.
     * <p>
     * this method is atomic, so all given keys are set at once.
     * It is not possible for clients to see that some of the keys were updated while others are unchanged.
     *
     * @param pairs the pairs to set.
     * @return
     *      if the all the keys were set, return true;
     *      if no key was set (at least one key already existed), return false.
     * @throws IllegalParameterException if pairs is empty.
     * @throws JredicException if some other err occur.
     */
    boolean msetnx(Pair ... pairs);

    /**
     * this method works exactly like {@link #setex(String, int, String)} with the sole difference
     * that the expire time is specified in milliseconds instead of seconds.
     *
     * @param key the key to operate.
     * @param millis expire time in milliseconds.
     * @param value the value to set.
     * @throws IllegalParameterException if key is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    void psetex(String key, long millis, String value);

    /**
     * Set key to hold the string value.
     * If key already holds a value, it is overwritten, regardless of its type.
     *
     * @param key the key to operate.
     * @param value the value to set.
     * @throws IllegalParameterException if key is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    void set(String key, String value);

    /**
     * Set key to hold the string value.
     * If key already holds a value, it is overwritten, regardless of its type.
     *
     * @param key the key to operate.
     * @param value the value to set.
     * @param optionBuilder the builder for set options.
     * @throws IllegalParameterException if key is blank OR value is blank OR optionBuilder is null.
     * @throws JredicException if some other err occur.
     */
    void set(String key, String value, SetOptionBuilder optionBuilder);

    /**
     * Set key to hold the string value.
     * If key already holds a value, it is overwritten, regardless of its type.
     *
     * @param key the key to operate.
     * @param value the value to set.
     * @param optionBuilder the builder for set options.
     * @throws IllegalParameterException if key is blank OR value is blank OR optionBuilder is null.
     * @throws JredicException if some other err occur.
     */
    void set(String key, String value, SetOptionBuilder optionBuilder, ConditionMeetListener meetListener);

    /**
     * Sets or clears the bit at offset in the string value stored at key.
     *
     * @param key the key to operate.
     * @param offset the offset to set bit.
     * @param bit the value (see {@link Bit}) to set.
     * @return
     *      the original bit value stored at offset.
     * @throws IllegalParameterException if key is blank OR bit is null.
     * @throws JredicException if some other err occur.
     */
    long setBit(String key, int offset, Bit bit);

    /**
     * Set key to hold the string value and set key to timeout after a given number of seconds.
     *
     * @param key the key to operate.
     * @param seconds expire time in seconds.
     * @param value the value to set.
     * @throws IllegalParameterException if key is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    void setex(String key, int seconds, String value);

    /**
     * Overwrites part of the string stored at key, starting at the specified offset, for the entire length of value.
     * If the offset is larger than the current length of the string at key,
     * the string is padded with zero-bytes to make offset fit.
     * Non-existing keys are considered as empty strings,
     * so this command will make sure it holds a string large enough to be able to set value at offset.
     *
     * @param key the key to operate.
     * @param offset the index to start set.
     * @param value the value to set.
     * @return
     *      the length of the string after it was modified by this method.
     * @throws IllegalParameterException if key is blank OR value is blank.
     * @throws JredicException if some other err occur.
     */
    long setRange(String key, int offset, String value);

    /**
     * Returns the length of the string value stored at key.
     * if key holds a non-string value, a exception will be throwed.
     *
     * @param key the key to operate.
     * @return
     *      the length of the string at key, or 0 when key does not exist.
     * @throws IllegalParameterException if key is blank.
     * @throws JredicException if some other err occur.
     */
    long strLen(String key);

}
