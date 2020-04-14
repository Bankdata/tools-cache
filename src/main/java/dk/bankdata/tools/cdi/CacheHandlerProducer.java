package dk.bankdata.tools.cdi;

import dk.bankdata.tools.CacheHandler;
import dk.bankdata.tools.CacheHandlerImpl;
import dk.bankdata.tools.CacheHandlerStub;
import dk.bankdata.tools.domain.Environment;
import dk.bankdata.tools.domain.Profile;
import dk.bankdata.tools.factory.JedisSentinelPoolFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class CacheHandlerProducer {
    @Inject
    private Environment environment;

    @Inject
    JedisSentinelPoolFactory jedisSentinelPoolFactory;

    @Produces
    public CacheHandler get() {
        if (environment.getProfile().equals(Profile.LOCAL)) {
            return new CacheHandlerStub();
        }

        return new CacheHandlerImpl(jedisSentinelPoolFactory);
    }
}
