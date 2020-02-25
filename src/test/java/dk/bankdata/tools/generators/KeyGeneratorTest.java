package dk.bankdata.tools.generators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.bankdata.api.jaxrs.jwt.JwtToken;
import org.jose4j.jwt.JwtClaims;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyGeneratorTest {
    @InjectMocks
    KeyGenerator keyGenerator;

    @Test
    public void shouldGenerateMiddlewareKey() throws Exception {
        JwtClaims jwtClaims = mock(JwtClaims.class);
        when(jwtClaims.getSubject()).thenReturn("HOB0510501603JNP");
        when(jwtClaims.getClaimValue("scope"))
                .thenReturn("digitalbanking replayId:28cf92c7-0a41-4818-b084-9b6828ea229d");

        JwtToken jwtToken = mock(JwtToken.class);
        when(jwtToken.getJwtClaims()).thenReturn(jwtClaims);

        String result = keyGenerator.getMiddlewareCookieCacheKey(jwtToken);

        Assert.assertEquals("BDJSESSIONID/HOB0510501603JNP/28cf92c7-0a41-4818-b084-9b6828ea229d", result);
    }


}