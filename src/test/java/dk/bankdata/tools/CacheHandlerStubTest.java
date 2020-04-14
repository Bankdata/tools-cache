package dk.bankdata.tools;

import dk.bankdata.tools.domain.Profile;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CacheHandlerStubTest {
    @InjectMocks
    private CacheHandlerStub cache;

    @Test
    public void shouldGet1() {
        Optional<byte[]> o = cache.get("some-key".getBytes());
        Assert.assertFalse(o.isPresent());
    }

    @Test
    public void shouldGet2() {
        Optional<String> s = cache.get("some-key");
        Assert.assertFalse(s.isPresent());
    }

    @Test
    public void shouldGet3() {
        Optional<Profile> profile = cache.get("some-key".getBytes(), Profile.class);
        Assert.assertFalse(profile.isPresent());
    }

    @Test
    public void shouldGet4() {
        Optional<Profile> profile = cache.get("some-key", Profile.class);
        Assert.assertFalse(profile.isPresent());
    }

    @Test
    public void shouldGetList() {
        //Optional<List<Profile>> list = cache.getList("some-key", Profile.class);
        //Assert.assertFalse(list.isPresent());
    }
}