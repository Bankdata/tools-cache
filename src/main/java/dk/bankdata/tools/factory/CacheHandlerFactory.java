package dk.bankdata.tools.factory;

import dk.bankdata.tools.CacheHandlerImpl;
import dk.bankdata.tools.CacheHandlerStub;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CacheHandlerFactory {
    private CacheHandlerImpl cacheHandlerImpl;
    private CacheHandlerStub cacheHandlerStub;

    public CacheHandlerStub getCacheHandlerStub() {
        if (cacheHandlerStub == null) {
            cacheHandlerStub = new CacheHandlerStub();
        }

        return cacheHandlerStub;
    }

    public CacheHandlerImpl getCacheHandlerImpl(JedisSentinelPoolFactory jedisSentinelPoolFactory) {
        if (cacheHandlerImpl == null) {
            cacheHandlerImpl = new CacheHandlerImpl(jedisSentinelPoolFactory);
        }

        return cacheHandlerImpl;
    }
}
