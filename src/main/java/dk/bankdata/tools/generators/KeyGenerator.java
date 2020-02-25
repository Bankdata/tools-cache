package dk.bankdata.tools.generators;

import dk.bankdata.api.jaxrs.jwt.JwtToken;
import dk.bankdata.tools.CacheHandler;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KeyGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(CacheHandler.class);

    public String getMiddlewareCookieCacheKey(JwtToken jwtToken) {
        try {
            JwtClaims jwtClaims = jwtToken.getJwtClaims();
            String sub = jwtClaims.getSubject();
            String scope = jwtClaims.getClaimValue("scope").toString();

            String[] splits = scope.split(" ");

            String replayid = Arrays.stream(splits).filter(s ->
                    s.toLowerCase().startsWith("replayid")).findAny().orElse(null);

            if (replayid == null) {
                throw new IllegalArgumentException("JWT doesnt support replayId.");
            }

            String guid = replayid.split(":")[1];

            return "BDJSESSIONID/" + sub + "/" + guid;
        } catch (Exception e) {
            String errorMessage = e.getCause() == null ? e.getMessage() : e.getMessage() + " || " +
                    e.getCause().getMessage();

            LOG.error("Failed to getKey. " + errorMessage, e);
            return "";
        }
    }
}
