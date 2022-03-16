package djajkov.app;

import java.io.Serializable;

public class Institution implements Serializable {
    private String name;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Institution() {
    }

    private Institution(String name) {
        this();
        this.name = name;
    }

    public Institution(String name, Location location) {
        this(name);
        this.location = location;
    }
}
