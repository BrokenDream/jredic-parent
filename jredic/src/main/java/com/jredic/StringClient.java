package com.jredic;

import com.jredic.command.sub.Bit;
import com.jredic.command.sub.BitOP;
import com.jredic.command.sub.ConditionMeetListener;
import com.jredic.command.sub.SetOptionBuilder;
import com.jredic.exception.IllegalParameterException;
import com.jredic.exception.JredicException;
import java.util.List;
import java.util.Map;

/**
 * Define operations for 'String' commands.
 * See {@link com.jredic.command.StringCommand}
 *
 * @author David.W
 */
public interface StringClient {

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
    void mset(Map<String, String> pairs);

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
    boolean msetnx(Map<String, String> pairs);

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
