package dk.bankdata.tools.domain;

public enum Profile {
    LOCAL,
    SERVER;

    public static Profile fromString(String profile) {
        if (profile.toLowerCase().equals("local")) {
            return LOCAL;
        } else {
            return SERVER;
        }
    }
}
