package dk.bankdata.tools;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CacheHandler {
    long llen(String key);

    long llen(byte[] key);

    void rpush(String key, String payload);

    void rpush(String key, Object payload);

    void lpush(String key, String payload);

    void lpush(String key, Object payload);

    void set(String key, String payload);

    void set(String key, String payload, int ttlInSeconds);

    void set(String key, Object payload);

    void set(String key, Object payload, int ttlInSeconds);

    void set(byte[] key, Serializable payload);

    void set(byte[] key, Serializable payload, int ttlInSeconds);

    void set(String key, List<Object> payload);

    void set(String key, List<Object> payload, int ttlInSeconds);

    boolean exists(String key);

    boolean exists(byte[] key);

    Optional<String> get(String key);

    Optional<byte[]> get(byte[] key);

    <T> Optional<T> get(String key, Class<T> classToReturn);

    <T> Optional<T> get(byte[] key, Class<T> classToReturn);

    <T> Optional<List<T>> getList(String key, Class<T> classInList);

    Optional<String> rpop(String key);

    Optional<byte[]> rpop(byte[] key);

    <T> Optional<T> rpop(String key, Class<T> classToReturn);

    <T> Optional<T> rpop(byte[] key, Class<T> classToReturn);

    Optional<String> lpop(String key);

    Optional<byte[]> lpop(byte[] key);

    <T> Optional<T> lpop(String key, Class<T> classToReturn);

    <T> Optional<T> lpop(byte[] key, Class<T> classToReturn);

    void delete(String key);

    void delete(byte[] key);

    void initialize();
}
