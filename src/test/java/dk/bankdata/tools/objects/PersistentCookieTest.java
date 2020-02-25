package dk.bankdata.tools.objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Cookie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersistentCookieTest {
    @Test
    public void shouldCreatePersistantCookie() {
        Cookie cookie = mock(Cookie.class);
        when(cookie.getName()).thenReturn("cookieName");
        when(cookie.getValue()).thenReturn("cookieValue");

        PersistentCookie persistentCookie = new PersistentCookie(cookie);

        Assert.assertNotNull(persistentCookie);
        Assert.assertEquals("cookieName", persistentCookie.getName());
        Assert.assertEquals("cookieValue", persistentCookie.getValue());
    }

}