package com.jredic;

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
     *
     * @param keys the keys to remove.
     * @return
     *      The number of keys that were removed.
     */
    long del(String ... keys);

    /**
     * Removes the specified key.
     *
     * @param key the key to remove.
     * @return
     *      true if key was removed;false if key does not exist.
     */
    boolean del(String key);

    /**
     * Serialize the value stored at key in a Redis-specific format and return it to the user.
     * <p>
     * The returned value can be synthesized back into a Redis key using the RESTORE command.
     * <p>
     * The serialization format is opaque and non-standard, however it has a few semantic characteristics:
     * <li>
     *     It contains a 64-bit checksum that is used to make sure errors will be detected.
     *     The RESTORE command makes sure to check the checksum before synthesizing a key using the serialized value.
     * </li>
     * <li>
     *     Values are encoded in the same format used by RDB.
     * </li>
     * <li>
     *     An RDB version is encoded inside the serialized value, so that different Redis versions with
     *     incompatible RDB formats will refuse to process the serialized value.
     * </li>
     * <p>
     * The serialized value does NOT contain expire information.
     * In order to capture the time to live of the current value the PTTL command should be used.
     *
     * @param key the key to dump.
     * @return
     *      the serialized value.If key does not exist a nil bulk reply is returned.
     */
    String dump(String key);

    /**
     * Test if the specified key exists.
     *
     * @param key the key to test.
     * @return
     *      if the key exists,return true;otherwise false.
     */
    boolean exists(String key);

    /**
     * Set a timeout on key. After the timeout has expired, the key will automatically be deleted.
     *
     * @param key the key to expire
     * @param seconds the expire time in seconds
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     */
    boolean expire(String key, int seconds);

    /**
     * EXPIREAT has the same effect and semantic as EXPIRE, but instead of specifying the number of
     * seconds representing the TTL (time to live), it takes an absolute Unix timestamp
     * (seconds since January 1, 1970). A timestamp in the past will delete the key immediately.
     *
     * @param key the key to expire
     * @param unixTimeInSeconds the time when the key expire
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     */
    boolean expireAt(String key, long unixTimeInSeconds);

    /**
     * This method works exactly like {@link #expire(String, int)}.
     * but the time to live of the key is specified in milliseconds instead of seconds.
     *
     * @param key the key to expire
     * @param millis the expire time in milliseconds
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
     */
    boolean pexpire(String key, long millis);

    /**
     * This method works exactly like {@link #expireAt(String, long)}.
     * but the Unix time at which the key will expire is specified in milliseconds instead of seconds.
     *
     * @param key the key to expire
     * @param unixTimeInMillis the time when the key expire
     * @return
     *      if the timeout was set,return true;
     *      if key does not exist,return false.
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
     */
    List<String> keys(String pattern);

    /**
     * Move key from the currently selected database to the specified destination database.
     * When key already exists in the destination database, or it does not exist in the source database,
     * it does nothing. It is possible to use MOVE as a locking primitive because of this.
     *
     * @param key the key to move
     * @param dbIndex the index of dest db.
     * @return
     *      if key was moved,return true;otherwise return false.
     */
    boolean move(String key, int dbIndex);

    /**
     * Remove the existing timeout on key, turning the key from volatile (a key with an expire set)
     * to persistent (a key that will never expire as no timeout is associated).
     *
     * @param key the key to persist.
     * @return
     *      if the timeout was removed,return true;
     *      if key does not exist or does not have an associated timeout,return false.
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
     * @param key the key to get TTL.
     * @return
     *      TTL in seconds, or a negative value in order to signal an error.
     */
    long ttl(String key);

    /**
     * Like {@link #ttl(String)} this method returns the remaining time to live of a key
     * that has an expire set, with the sole difference that {@link #ttl(String)} returns
     * the amount of remaining time in seconds while {@link #pttl(String)} returns it in milliseconds.
     *
     * @param key the key to get TTL in milliseconds.
     * @return
     *      TTL in milliseconds, or a negative value in order to signal an error.
     */
    long pttl(String key);

    /**
     * Return a random key from the currently selected database.
     *
     * @return
     *      the random key, or null when the database is empty.
     */
    String randomKey();

    /**
     * Renames key to newkey. It throw an {@link JredicException} when key does not exist.
     * If newkey already exists it is overwritten, when this happens RENAME executes
     * an implicit DEL operation, so if the deleted key contains a very big value
     * it may cause high latency even if RENAME itself is usually a constant-time operation.
     *
     * @param key the key to rename.
     * @param newKey the new name.
     */
    void rename(String key, String newKey);

    /**
     * Renames key to newkey if newkey does not yet exist.
     * It throw an {@link JredicException} when key does not exist.
     *
     * @param key the key to rename.
     * @param newKey the new name.
     * @return
     *      true if key was renamed to newKey;false if newKey already exists.
     */
    boolean renamenx(String key, String newKey);

    /**
     * Create a key associated with a value that is obtained by deserializing
     * the provided serialized value (obtained via DUMP).If ttl is 0 the key
     * is created without any expire, otherwise the specified expire time (in milliseconds) is set.
     *
     * @param key the key to restore.
     * @param ttl expire time in milliseconds.
     * @param serializedValue serialized value associate to the key.
     */
    void restore(String key, int ttl, String serializedValue);

    /**
     * Returns the type of the value stored at key.
     * @see RedisDataType
     *
     * @param key the key to get type.
     * @return
     *      the type of value at key;null if key does not exist.
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
     * @param key the key to be appended.
     * @param value appended value.
     * @return
     *      the length of the string after the append.
     */
    long append(String key, String value);

    /**
     * Count the number of set bits (population counting) in a string.
     *
     * @param key the key to count bit.
     * @return
     *      The number of bits set to 1.
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
     */
    long bitCount(String key, int start, int end);

    void set(String key, String value);

    String get(String key);

}
