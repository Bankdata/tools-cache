package dk.bankdata.tools;

import dk.bankdata.tools.objects.PersistentCookie;
import javax.ws.rs.core.Cookie;
import org.junit.Assert;
import org.junit.Test;

public class PersistentCookieTest {
    @Test
    public void shouldCreatePersistantCookie() {
        Cookie cookie = new Cookie("some-name", "some-value");
        PersistentCookie persistentCookie = new PersistentCookie(cookie);

        Assert.assertNotNull(persistentCookie);
        Assert.assertEquals("some-name", persistentCookie.getName());
        Assert.assertEquals("some-value", persistentCookie.getValue());
    }
}