package com.jredic;

import java.util.List;

/**
 * @author David.W
 */
public interface Jredic {

    /*
     * ops to manage 'Key'
     */

    /**
     * Removes the specified keys.
     *
     * @param key the key to remove.
     * @return
     *      The number of keys that were removed.
     */
    long del(String ... key);

    /**
     * Serialize the value stored at key in a Redis-specific format and return it to the user.
     * <p>The returned value can be synthesized back into a Redis key using the RESTORE command.
     * <p>The serialization format is opaque and non-standard, however it has a few semantic characteristics:
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
     *  <p>The serialized value does NOT contain expire information.
     *  In order to capture the time to live of the current value the PTTL command should be used.
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
     * This method works exactly like expire
     * but the time to live of the key is specified in milliseconds instead of seconds.
     *
     * @param key the key to expire
     * @param millis the expire time in milliseconds
     * @return
     */
    boolean pexpire(String key, long millis);

    /**
     * PEXPIREAT has the same effect and semantic as EXPIREAT,
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
     *
     * @param pattern the key pattern.
     * @return
     *      list of keys matching pattern.
     */
    List<String> keys(String pattern);

    /**
     * Move key from the currently selected database (see SELECT) to the specified destination database.
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

    long ttl(String key);

    long pttl(String key);

    String randomKey();

    void rename(String key, String newKey);

    boolean renamenx(String key, String newKey);

    RedisDataType type(String key);

    void set(String key, String value);

    String get(String key);

}
