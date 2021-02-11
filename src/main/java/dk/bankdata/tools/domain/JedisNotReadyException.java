package dk.bankdata.tools.domain;

import redis.clients.jedis.exceptions.JedisException;

public class JedisNotReadyException extends JedisException {

    public JedisNotReadyException(String message) {
        super(message);
    }
}
