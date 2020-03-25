package dk.bankdata.tools.cdi;

import dk.bankdata.tools.domain.Environment;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EnvironmentProducer {

    @Produces
    public Environment create() {
        String masterName = loadSystemEnvironmentVariable("REDIS_MASTERNAME");
        String password = getSystemVariable("REDIS_PASSWORD") == null ? null : loadSystemEnvironmentVariable("REDIS_PASSWORD");
        String sentinels = loadSystemEnvironmentVariable("REDIS_SENTINELS");
        String profile = getSystemVariable("REDIS_PROFILE") == null ? "server" : loadSystemEnvironmentVariable("REDIS_PROFILE");

        return new Environment(masterName, password, sentinels, profile);
    }

    protected String loadSystemEnvironmentVariable(String variableName) {
        String value = System.getenv(variableName);

        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Expected environment variable: " + variableName);
        }

        return value;
    }

    protected String getSystemVariable(String variable) {
        return System.getenv(variable);
    }
}