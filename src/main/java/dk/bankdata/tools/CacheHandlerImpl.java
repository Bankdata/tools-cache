package dk.bankdata.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bankdata.tools.factory.JedisSentinelPoolFactory;
import dk.bankdata.tools.factory.ObjectMapperFactory;
import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class CacheHandlerImpl implements CacheHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CacheHandlerImpl.class);
    private JedisSentinelPoolFactory factory;

    private CacheHandlerImpl() {

    }

    public CacheHandlerImpl(JedisSentinelPoolFactory jedisSentinelPoolFactory) {
        this.factory = jedisSentinelPoolFactory;

    }

    //*************************************************************************************\\
    //******************************* INSERT INTO CACHE ***********************************\\
    //*************************************************************************************\\

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param object item to cache - Will be converted to json
     */
    public void set(String key, Object object) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            String payload = objectMapper.writeValueAsString(object);
            this.set(key, payload, 0);
        } catch (JsonProcessingException e) {
            throw createRunTimeException("Unable to process object - Error was " + e.getMessage(), e);
        }
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param object item to cache - Will be converted to json
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    public void set(String key, Object object, int ttlInSeconds) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            String payload = objectMapper.writeValueAsString(object);
            this.set(key, payload, ttlInSeconds);
        } catch (JsonProcessingException e) {
            throw createRunTimeException("Unable to process object - Error was " + e.getMessage(), e);
        }
    }

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * @param key unique cache key
     * @param payload item to cache
     */
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
    public void set(String key, String payload, int ttlInSeconds) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }

            jedis.set(key, payload);

            if (ttlInSeconds != 0) {
                jedis.expire(key, ttlInSeconds);
            }
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + key + "] ", e);
        }
    }

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * Utilizes SerializationUtils.serialize() to cache the payload
     * @param key unique cache key
     * @param payload item to cache
     */
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
    public void set(byte[] key, Serializable payload, int ttlInSeconds) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }

            jedis.rpush(key, SerializationUtils.serialize(payload));

            if (ttlInSeconds != 0) {
                jedis.expire(key, ttlInSeconds);
            }
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + new String(key) + "] ", e);
        }
    }

    //*************************************************************************************\\
    //******************************* EXISTS IN CACHE *************************************\\
    //*************************************************************************************\\

    /**
     * Check to see if the cache contains the provided key.
     * @param key key to check
     * @return true if exists or else false
     */
    public boolean exists(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {

            return jedis.exists(key);
        } catch (Exception e) {
            throw createRunTimeException("Failed to check key [" + key + "] ", e);
        }
    }

    /**
     * Check to see if the cache contains the provided key.
     * @param key key to check
     * @return true if exists or else false
     */
    public boolean exists(byte[] key) {
        try (Jedis jedis = factory.getPool().getResource()) {

            return jedis.exists(key);
        } catch (Exception e) {
            throw createRunTimeException("Failed to check key [" + new String(key) + "] ", e);
        }
    }

    //*************************************************************************************\\
    //******************************* GET FROM CACHE **************************************\\
    //*************************************************************************************\\

    /**
     * Get a cached item by key.
     * @param key key of the item
     * @return payload
     */
    public String get(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            throw createRunTimeException("Failed to get key [" + key + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     * @param key key of the item
     * @return the cached bytes
     */
    public byte[] get(byte[] key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            return jedis.lpop(key);
        } catch (Exception e) {
            throw createRunTimeException("Failed to get key [" + new String(key) + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     * @param key key of the item
     * @param classToReturn the type of the returned class
     * @param <T> generic type
     * @return the provided class object
     */
    public <T> T get(String key, Class<T> classToReturn) {
        try (Jedis jedis = factory.getPool().getResource()) {
            String payload = jedis.get(key);
            ObjectMapper om = ObjectMapperFactory.getInstance();

            return om.readValue(payload, classToReturn);
        } catch (Exception e) {
            throw createRunTimeException("Failed to get key [" + key + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     * @param key key of the item
     * @param classToReturn the type of the returned class
     * @param <T> generic type
     * @return the provided class object
     */
    public <T> T get(byte[] key, Class<T> classToReturn) {
        try (Jedis jedis = factory.getPool().getResource()) {

            byte[] payload = jedis.lpop(key);
            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

            return objectMapper.readValue(payload, classToReturn);
        } catch (Exception e) {
            throw createRunTimeException("Failed to get key [" + new String(key) + "]", e);
        }
    }

    //*************************************************************************************\\
    //******************************* REMOVE FROM CACHE ***********************************\\
    //*************************************************************************************\\

    /**
     * Removes a cached item.
     * @param key the key to delete
     */
    public void delete(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }
        }
    }

    /**
     * Removes a cached item.
     * @param key the key to delete
     */
    public void delete(byte[] key) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }
        }
    }

    private RuntimeException createRunTimeException(String message, Throwable t) {
        String errorMessage = t.getCause() == null ? t.getMessage() : t.getMessage() + " || " +
                t.getCause().getMessage();

        LOG.error(message + " / " + errorMessage, t);

        return new RuntimeException(message);
    }

}
