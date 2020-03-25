package dk.bankdata.tools;

import java.io.Serializable;
import java.util.Optional;

public interface CacheHandler {
    void set(String key, String payload);

    void set(String key, String payload, int ttlInSeconds);

    void set(String key, Object payload);

    void set(String key, Object payload, int ttlInSeconds);

    void set(byte[] key, Serializable payload);

    void set(byte[] key, Serializable payload, int ttlInSeconds);

    boolean exists(String key);

    boolean exists(byte[] key);

    Optional<String> get(String key);

    Optional<byte[]> get(byte[] key);

    <T> Optional<T> get(String key, Class<T> classToReturn);

    <T> Optional<T> get(byte[] key, Class<T> classToReturn);

    void delete(String key);

    void delete(byte[] key);
}
