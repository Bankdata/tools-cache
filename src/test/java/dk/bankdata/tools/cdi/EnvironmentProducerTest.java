package dk.bankdata.tools.cdi;

import static org.mockito.Mockito.doReturn;

import dk.bankdata.tools.domain.Environment;
import dk.bankdata.tools.domain.Profile;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentProducerTest {
    @InjectMocks @Spy
    EnvironmentProducer producer;

    @Test
    public void shouldCreateEnvironment() {
        doReturn("some-password").when(producer).getSystemVariable("REDIS_PASSWORD");
        doReturn("some-profile").when(producer).getSystemVariable("REDIS_PROFILE");

        doReturn("some-master").when(producer).loadSystemEnvironmentVariable("REDIS_MASTERNAME");
        doReturn("some-password").when(producer).loadSystemEnvironmentVariable("REDIS_PASSWORD");
        doReturn("some-sentinel-1:123,some-sentinel-2:456").when(producer).loadSystemEnvironmentVariable("REDIS_SENTINELS");
        doReturn("server").when(producer).loadSystemEnvironmentVariable("REDIS_PROFILE");

        Environment environment = producer.create();

        Set<String> sentinels = new HashSet<>();
        sentinels.add("some-sentinel-1:123");
        sentinels.add("some-sentinel-2:456");

        Assert.assertEquals("some-master", environment.getMasterName());
        Assert.assertEquals("some-password", environment.getPassword());
        Assert.assertEquals(sentinels, environment.getSentinels());
        Assert.assertEquals(Profile.SERVER, environment.getProfile());
    }

    @Test
    public void shouldCreateEnvironmentWithNoPassword() {
        doReturn(null).when(producer).getSystemVariable("REDIS_PASSWORD");
        doReturn("some-profile").when(producer).getSystemVariable("REDIS_PROFILE");

        doReturn("some-master").when(producer).loadSystemEnvironmentVariable("REDIS_MASTERNAME");
        doReturn("some-sentinel-1:123,some-sentinel-2:456").when(producer).loadSystemEnvironmentVariable("REDIS_SENTINELS");
        doReturn("server").when(producer).loadSystemEnvironmentVariable("REDIS_PROFILE");

        Environment environment = producer.create();

        Set<String> sentinels = new HashSet<>();
        sentinels.add("some-sentinel-1:123");
        sentinels.add("some-sentinel-2:456");

        Assert.assertEquals("some-master", environment.getMasterName());
        Assert.assertNull(environment.getPassword());
        Assert.assertEquals(sentinels, environment.getSentinels());
        Assert.assertEquals(Profile.SERVER, environment.getProfile());
    }

    @Test
    public void shouldCreateEnvironmentWithLocalProfile() {
        doReturn("local").when(producer).getSystemVariable("REDIS_PROFILE");
        doReturn("local").when(producer).loadSystemEnvironmentVariable("REDIS_PROFILE");

        Environment environment = producer.create();

        Assert.assertEquals("", environment.getMasterName());
        Assert.assertEquals(null, environment.getPassword());
        Assert.assertEquals(1, environment.getSentinels().size());
        Assert.assertEquals(Profile.LOCAL, environment.getProfile());
    }
}
