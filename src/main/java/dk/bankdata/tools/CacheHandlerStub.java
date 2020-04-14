package dk.bankdata.tools;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CacheHandlerStub implements CacheHandler {

    //*************************************************************************************\\
    //******************************* INSERT INTO CACHE ***********************************\\
    //*************************************************************************************\\

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload item to cache
     */
    @Override
    public void set(String key, String payload) {
        this.set(key, payload, 0);
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload item to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
    public void set(String key, String payload, int ttlInSeconds) {
        // Do nothing
    }

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload item to cache
     */
    @Override
    public void set(String key, Object payload) {
        // Do nothing
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload item to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
    public void set(String key, Object payload, int ttlInSeconds) {
        // Do nothing
    }

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * Utilizes SerializationUtils.serialize() to cache the payload
     * @param key unique cache key
     * @param payload item to cache
     */
    @Override
    public void set(byte[] key, Serializable payload) {
        this.set(key, payload, 0);
    }

    /**
     * Set a cache with expire time.
     * If the provided key exists then it will be overwritten
     * Utilizes SerializationUtils.serialize() to cache the payload
     * @param key unique cache key
     * @param payload item to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
    public void set(byte[] key, Serializable payload, int ttlInSeconds) {
        // Do nothing
    }

    /**
     * Set a cache with expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload list of items to cache
     */
    @Override
    public void set(String key, List<Object> payload) {
        // Do nothing
    }

    /**
     * Set a cache with expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload list of items to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
    public void set(String key, List<Object> payload, int ttlInSeconds) {
        // Do nothing
    }

    //*************************************************************************************\\
    //******************************* EXISTS IN CACHE *************************************\\
    //*************************************************************************************\\

    /**
     * Check to see if the cache contains the provided key.
     * @param key key to check
     * @return true if exists or else false
     */
    @Override
    public boolean exists(String key) {
        return false;
    }

    /**
     * Check to see if the cache contains the provided key.
     * @param key key to check
     * @return true if exists or else false
     */
    @Override
    public boolean exists(byte[] key) {
        return false;
    }

    //*************************************************************************************\\
    //******************************* GET FROM CACHE **************************************\\
    //*************************************************************************************\\

    /**
     * Get a cached item by key.
     * @param key key of the item
     * @return payload
     */
    @Override
    public Optional<String> get(String key) {
        return Optional.empty();
    }

    /**
     * Get a cached item by key.
     * @param key key of the item
     * @return the cached bytes
     */
    @Override
    public Optional<byte[]> get(byte[] key) {
        return Optional.empty();
    }

    /**
     * Get a cached item by key.
     * @param <T> generic type
     * @param key key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> get(String key, Class<T> classToReturn) {
        return Optional.empty();
    }

    /**
     * Get a cached item by key.
     * @param <T> generic type
     * @param key key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> get(byte[] key, Class<T> classToReturn) {
        return Optional.empty();
    }

    /**
     * Get a cached list of items by key.
     * @param <T> generic type
     * @param key key of the item
     * @param classInList the type of the object in the list
     * @return a list of the provided class objects
     */
    @Override
    public <T> Optional<List<T>> getList(String key, Class<T> classInList) {
        return Optional.empty();
    }

    //*************************************************************************************\\
    //******************************* REMOVE FROM CACHE ***********************************\\
    //*************************************************************************************\\

    /**
     * Removes a cached item.
     * @param key the key to delete
     */
    @Override
    public void delete(String key) {
        // Do nothing
    }

    /**
     * Removes a cached item.
     * @param key the key to delete
     */
    @Override
    public void delete(byte[] key) {
        // Do nothing
    }

    /**
     * Should be called a service startup to prevent thread overflow.
     */
    @Override
    public void initialization() {
        //Do nothing
    }

}
