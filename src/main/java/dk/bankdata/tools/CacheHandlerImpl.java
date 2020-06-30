package dk.bankdata.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dk.bankdata.tools.factory.JedisSentinelPoolFactory;
import dk.bankdata.tools.factory.ObjectMapperFactory;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
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
     *
     * @param key     unique cache key
     * @param payload item to cache
     */
    @Override
    public void set(String key, String payload) {
        this.set(key, payload, 0);
    }

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key    unique cache key
     * @param object item to cache - Will be converted to json
     */
    @Override
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
     *
     * @param key          unique cache key
     * @param payload      item to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
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
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key          unique cache key
     * @param object       item to cache - Will be converted to json
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
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
     * Set a cache with expire time.
     * If the provided key exists then it will be overwritten
     * Utilizes SerializationUtils.serialize() to cache the payload
     *
     * @param key          unique cache key
     * @param payload      item to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
    public void set(byte[] key, Serializable payload, int ttlInSeconds) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }

            jedis.set(key, SerializationUtils.serialize(payload));

            if (ttlInSeconds != 0) {
                jedis.expire(key, ttlInSeconds);
            }
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + new String(key) + "] ", e);
        }
    }

    /**
     * Set a cache with expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key     unique cache key
     * @param payload list of items to cache
     */
    @Override
    public void set(String key, List<Object> payload) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            String json = objectMapper.writeValueAsString(payload);
            this.set(key, json);
        } catch (JsonProcessingException e) {
            throw createRunTimeException("Unable to process object list - Error was " + e.getMessage(), e);
        }
    }

    /**
     * Set a cache with expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key          unique cache key
     * @param payload      list of items to cache
     * @param ttlInSeconds how many seconds should the payload be cached
     */
    @Override
    public void set(String key, List<Object> payload, int ttlInSeconds) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            String json = objectMapper.writeValueAsString(payload);
            this.set(key, json, ttlInSeconds);
        } catch (JsonProcessingException e) {
            throw createRunTimeException("Unable to process object list - Error was " + e.getMessage(), e);
        }
    }

    /**
     * Set a cache without expire time.
     * If the provided key exists then it will be overwritten
     * Utilizes SerializationUtils.serialize() to cache the payload
     *
     * @param key     unique cache key
     * @param payload item to cache
     */
    @Override
    public void set(byte[] key, Serializable payload) {
        this.set(key, payload, 0);
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key     unique cache key
     * @param payload item to cache - Will be converted to json
     */
    @Override
    public void rpush(String key, String payload) {
        try (Jedis jedis = factory.getPool().getResource()) {
            if (jedis.exists(key)) {
                jedis.del(key);
            }

            jedis.rpush(key, payload);
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + key + "] ", e);
        }
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key    unique cache key
     * @param object item to cache - Will be converted to json
     */
    @Override
    public void rpush(String key, Object object) {
        try (Jedis jedis = factory.getPool().getResource()) {
            if (jedis.exists(key)) {
                jedis.del(key);
            }

            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            String payload = objectMapper.writeValueAsString(object);

            jedis.rpush(key, payload);
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + key + "] ", e);
        }
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key     unique cache key
     * @param payload item to cache - Will be converted to json
     */
    @Override
    public void lpush(String key, String payload) {
        try (Jedis jedis = factory.getPool().getResource()) {
            if (jedis.exists(key)) {
                jedis.del(key);
            }

            jedis.lpush(key, payload);
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + key + "] ", e);
        }
    }

    /**
     * Set a cache with with an expire time.
     * If the provided key exists then it will be overwritten
     *
     * @param key    unique cache key
     * @param object item to cache - Will be converted to json
     */
    @Override
    public void lpush(String key, Object object) {
        try (Jedis jedis = factory.getPool().getResource()) {
            if (jedis.exists(key)) {
                jedis.del(key);
            }

            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            String payload = objectMapper.writeValueAsString(object);

            jedis.lpush(key, payload);
        } catch (Exception e) {
            throw createRunTimeException("Failed to set key [" + key + "] ", e);
        }
    }

    //*************************************************************************************\\
    //******************************* LENGTHS IN CACHE ************************************\\
    //*************************************************************************************\\


    @Override
    public long llen(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            if (jedis.exists(key)) {
                return jedis.llen(key);
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw createRunTimeException("Failed to get llen for key [" + key + "] ", e);
        }
    }

    @Override
    public long llen(byte[] key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            if (jedis.exists(key)) {
                return jedis.llen(key);
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw createRunTimeException("Failed to get llen for key [" + new String(key) + "] ", e);
        }
    }


    //*************************************************************************************\\
    //******************************* EXISTS IN CACHE *************************************\\
    //*************************************************************************************\\

    /**
     * Check to see if the cache contains the provided key.
     *
     * @param key key to check
     * @return true if exists or else false
     */
    @Override
    public boolean exists(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            throw createRunTimeException("Failed to check key [" + key + "] ", e);
        }
    }

    /**
     * Check to see if the cache contains the provided key.
     *
     * @param key key to check
     * @return true if exists or else false
     */
    @Override
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
     *
     * @param key key of the item
     * @return payload
     */
    @Override
    public Optional<String> get(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            Optional<String> result = Optional.empty();

            if (jedis.exists(key)) {
                String s = jedis.get(key);
                result = Optional.ofNullable(s);
            }

            return result;
        } catch (Exception e) {
            throw createRunTimeException("Failed to get key [" + key + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     *
     * @param key key of the item
     * @return the cached bytes
     */
    @Override
    public Optional<byte[]> get(byte[] key) {
        try (Jedis jedis = factory.getPool().getResource()) {
            Optional<byte[]> result = Optional.empty();

            if (jedis.exists(key)) {
                byte[] bytes = jedis.get(key);
                result = Optional.ofNullable(bytes);
            }

            return result;
        } catch (Exception e) {
            throw createRunTimeException("Failed to get key [" + new String(key) + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     *
     * @param <T>           generic type
     * @param key           key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> get(String key, Class<T> classToReturn) {
        String payload = null;
        try (Jedis jedis = factory.getPool().getResource()) {
            Optional<T> result = Optional.empty();

            if (jedis.exists(key)) {
                payload = jedis.get(key);
                ObjectMapper om = ObjectMapperFactory.getInstance();

                T t = om.readValue(payload, classToReturn);
                result = Optional.ofNullable(t);
            }

            return result;
        } catch (Exception e) {
            if (payload != null) {
                LOG.debug("Failed to parse key [" + key + "] with value [" + payload + "]");
            }
            throw createRunTimeException("Failed to get key [" + key + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     *
     * @param <T>           generic type
     * @param key           key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> get(byte[] key, Class<T> classToReturn) {
        byte[] payload = null;
        try (Jedis jedis = factory.getPool().getResource()) {
            Optional<T> result = Optional.empty();

            if (jedis.exists(key)) {
                payload = jedis.lpop(key);
                ObjectMapper om = ObjectMapperFactory.getInstance();

                T t = om.readValue(payload, classToReturn);
                result = Optional.ofNullable(t);
            }

            return result;
        } catch (Exception e) {
            if (payload != null) {
                LOG.debug("Failed to parse key [" + key + "] with value [" + payload.toString() + "]");
            }
            throw createRunTimeException("Failed to get key [" + new String(key) + "]", e);
        }
    }

    @Override
    public <T> Optional<List<T>> getList(String key, Class<T> classInList) {
        String payload = null;
        try (Jedis jedis = factory.getPool().getResource()) {
            Optional<List<T>> result = Optional.empty();

            if (jedis.exists(key)) {
                payload = jedis.get(key);
                ObjectMapper om = ObjectMapperFactory.getInstance();
                TypeFactory typeFactory = TypeFactory.defaultInstance();
                List<T> t = om.readValue(payload, typeFactory.constructCollectionType(List.class, classInList));

                result = Optional.ofNullable(t);
            }

            return result;
        } catch (Exception e) {
            if (payload != null) {
                LOG.debug("Failed to parse key [" + key + "] with value [" + payload.toString() + "]");
            }
            throw createRunTimeException("Failed to get key [" + key + "]", e);
        }
    }

    /**
     * Get a cached item by key.
     *
     * @param key key of the item
     * @return the provided class object
     */
    @Override
    public Optional<String> rpop(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param key key of the item
     * @return the provided class object
     */
    @Override
    public Optional<byte[]> rpop(byte[] key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param <T>           generic type
     * @param key           key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> rpop(String key, Class<T> classToReturn) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param <T>           generic type
     * @param key           key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> rpop(byte[] key, Class<T> classToReturn) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param key key of the item
     * @return the provided class object
     */
    @Override
    public Optional<String> lpop(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param key key of the item
     * @return the provided class object
     */
    @Override
    public Optional<byte[]> lpop(byte[] key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param <T>           generic type
     * @param key           key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> lpop(String key, Class<T> classToReturn) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a cached item by key.
     *
     * @param <T>           generic type
     * @param key           key of the item
     * @param classToReturn the type of the returned class
     * @return the provided class object
     */
    @Override
    public <T> Optional<T> lpop(byte[] key, Class<T> classToReturn) {
        throw new UnsupportedOperationException();
    }

    //*************************************************************************************\\
    //******************************* REMOVE FROM CACHE ***********************************\\
    //*************************************************************************************\\

    /**
     * Removes a cached item.
     *
     * @param key the key to delete
     */
    @Override
    public void delete(String key) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }
        }
    }

    /**
     * Removes a cached item.
     *
     * @param key the key to delete
     */
    @Override
    public void delete(byte[] key) {
        try (Jedis jedis = factory.getPool().getResource()) {

            if (jedis.exists(key)) {
                jedis.del(key);
            }
        }
    }

    /**
     * Should be called a service startup to prevent thread overflow.
     */
    @Override
    public void initialize() {
        try (Jedis jedis = factory.getPool().getResource()) {
            jedis.exists("Initialization-key");
        }
    }

    private RuntimeException createRunTimeException(String message, Throwable t) {
        String errorMessage = t.getCause() == null ? t.getMessage() : t.getMessage() + " || " +
            t.getCause().getMessage();

        LOG.error(message + " / " + errorMessage, t);

        return new RuntimeException(message, t);
    }

}
