package dk.bankdata.tools.cdi;

import static org.mockito.Mockito.when;

import dk.bankdata.tools.CacheHandler;
import dk.bankdata.tools.CacheHandlerImpl;
import dk.bankdata.tools.CacheHandlerStub;
import dk.bankdata.tools.domain.Environment;
import dk.bankdata.tools.domain.Profile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CacheHandlerProducerTest {
    @InjectMocks
    CacheHandlerProducer cacheHandlerProducer;

    @Mock
    private Environment environment;

    @Test
    public void shouldCreateStub() {
        when(environment.getProfile()).thenReturn(Profile.LOCAL);

        CacheHandler cacheHandler = cacheHandlerProducer.get();

        Assert.assertTrue(cacheHandler instanceof CacheHandlerStub);
    }

    @Test
    public void shouldCreateImpl() {
        when(environment.getProfile()).thenReturn(Profile.SERVER);

        CacheHandler cacheHandler = cacheHandlerProducer.get();

        Assert.assertTrue(cacheHandler instanceof CacheHandlerImpl);
    }

}