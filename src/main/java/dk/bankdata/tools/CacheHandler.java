package dk.bankdata.tools;

import java.io.Serializable;

public interface CacheHandler {
    void set(String key, String payload);

    void set(String key, String payload, int ttlInSeconds);

    void set(String key, Object payload);

    void set(String key, Object payload, int ttlInSeconds);

    void set(byte[] key, Serializable payload);

    void set(byte[] key, Serializable payload, int ttlInSeconds);

    boolean exists(String key);

    boolean exists(byte[] key);

    String get(String key);

    byte[] get(byte[] key);

    <T> T get(String key, Class<T> classToReturn);

    <T> T get(byte[] key, Class<T> classToReturn);

    void delete(String key);

    void delete(byte[] key);
}
