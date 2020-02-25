package dk.bankdata.tools.factory;

import dk.bankdata.tools.domain.Environment;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import redis.clients.jedis.JedisSentinelPool;

@ApplicationScoped
public class JedisSentinelPoolFactory {
    @Inject
    private Environment environment;

    private JedisSentinelPool sentinelPool;

    public JedisSentinelPool getPool() {
        if (sentinelPool == null) {
            sentinelPool = new JedisSentinelPool(environment.getMasterName(), environment.getSentinels(),
                    environment.getPassword());
        }

        return sentinelPool;
    }
}
