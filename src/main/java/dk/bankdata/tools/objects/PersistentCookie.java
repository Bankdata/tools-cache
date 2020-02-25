package dk.bankdata.tools.objects;

import java.io.Serializable;
import java.util.Objects;
import javax.ws.rs.core.Cookie;

public class PersistentCookie implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    private PersistentCookie() {

    }

    public PersistentCookie(Cookie cookie) {
        this.name = cookie.getName();
        this.value = cookie.getValue();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Cookie createCookie() {
        return new Cookie(name, value);
    }

    @Override
    public String toString() {
        return "PersistentCookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentCookie that = (PersistentCookie) o;
        return Objects.equals(name, that.name)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
