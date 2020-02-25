package dk.bankdata.tools.cdi;

import dk.bankdata.tools.domain.Environment;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EnvironmentProducer {

    @Produces
    public Environment create() {
        String masterName = loadSystemEnvironmentVariable("REDIS_MASTERNAME");
        String password = System.getenv("REDIS_PASSWORD") == null ? "" : loadSystemEnvironmentVariable("REDIS_PASSWORD");
        String sentinels = loadSystemEnvironmentVariable("REDIS_SENTINELS");
        String profile = System.getenv("REDIS_PROFILE") == null ? "" : loadSystemEnvironmentVariable("REDIS_PROFILE");

        return new Environment(masterName, password, sentinels, profile);
    }

    private String loadSystemEnvironmentVariable(String variableName) {
        String value = System.getenv(variableName);

        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Expected environment variable: " + variableName);
        }

        return value;
    }
}