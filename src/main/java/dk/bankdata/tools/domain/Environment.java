package dk.bankdata.tools.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Environment {
    private String masterName;
    private String password;
    private Set<String> sentinels;
    private Profile profile;

    public Environment(String masterName, String password, String sentinels, String profile) {
        this.masterName = masterName;
        this.password = password;
        this.sentinels = new HashSet<>(Arrays.asList(sentinels.split(",")));
        this.profile = Profile.fromString(profile);
    }

    public String getMasterName() {
        return masterName;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getSentinels() {
        return sentinels;
    }

    public Profile getProfile() {
        return profile;
    }
}
