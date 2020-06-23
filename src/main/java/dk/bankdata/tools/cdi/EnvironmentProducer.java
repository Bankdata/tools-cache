package dk.bankdata.tools.cdi;

import dk.bankdata.tools.domain.Environment;
import dk.bankdata.tools.domain.Profile;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EnvironmentProducer {

    @Produces
    public Environment create() {
        String masterName = "";
        String password = null;
        String sentinels = "";

        String profile = getSystemVariable("REDIS_PROFILE") == null ? "server" : loadSystemEnvironmentVariable("REDIS_PROFILE");

        if (Profile.fromString(profile).equals(Profile.SERVER)) {
            masterName = loadSystemEnvironmentVariable("REDIS_MASTERNAME");
            password = getSystemVariable("REDIS_PASSWORD") == null ? null : loadSystemEnvironmentVariable("REDIS_PASSWORD");
            sentinels = loadSystemEnvironmentVariable("REDIS_SENTINELS");
        }

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