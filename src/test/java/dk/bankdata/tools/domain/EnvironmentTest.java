package dk.bankdata.tools.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentTest {
    @Test
    public void shouldCreateEnvironment() {
        Environment environment = new Environment(
                "some-master",
                "some-password",
                "s1:123, s2:456",
                "server");

        Assert.assertEquals("some-master", environment.getMasterName());
        Assert.assertEquals("some-password", environment.getPassword());
        Assert.assertEquals(2, environment.getSentinels().size());
        Assert.assertEquals(Profile.SERVER, environment.getProfile());
    }
}