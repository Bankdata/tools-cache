package dk.bankdata.tools.cdi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.bankdata.tools.CacheHandler;
import dk.bankdata.tools.CacheHandlerImpl;
import dk.bankdata.tools.CacheHandlerStub;
import dk.bankdata.tools.domain.Environment;
import dk.bankdata.tools.domain.Profile;
import dk.bankdata.tools.factory.CacheHandlerFactory;
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

    @Mock
    private CacheHandlerFactory cacheHandlerFactory;

    @Test
    public void shouldCreateStub() {
        CacheHandlerStub stub = mock(CacheHandlerStub.class);
        when(cacheHandlerFactory.getCacheHandlerStub()).thenReturn(stub);

        when(environment.getProfile()).thenReturn(Profile.LOCAL);

        CacheHandler cacheHandler = cacheHandlerProducer.get();

        Assert.assertTrue(cacheHandler instanceof CacheHandlerStub);
    }

    @Test
    public void shouldCreateImpl() {
        CacheHandlerImpl impl = mock(CacheHandlerImpl.class);
        when(cacheHandlerFactory.getCacheHandlerImpl(any())).thenReturn(impl);

        when(environment.getProfile()).thenReturn(Profile.SERVER);

        CacheHandler cacheHandler = cacheHandlerProducer.get();

        Assert.assertTrue(cacheHandler instanceof CacheHandlerImpl);
    }

}