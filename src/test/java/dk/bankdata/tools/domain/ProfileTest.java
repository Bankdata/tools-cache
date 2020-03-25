package dk.bankdata.tools.domain;

import org.junit.Assert;
import org.junit.Test;

public class ProfileTest {
    @Test
    public void shouldCreateLocalProfile() {
        Profile profile = Profile.fromString("local");

        Assert.assertEquals(Profile.LOCAL, profile);
    }

    @Test
    public void shouldCreateServerProfile() {
        Profile profile = Profile.fromString("server");

        Assert.assertEquals(Profile.SERVER, profile);
    }

    @Test
    public void shouldCreateServerProfileAsDefault() {
        Profile profile = Profile.fromString("unknown");

        Assert.assertEquals(Profile.SERVER, profile);
    }

}