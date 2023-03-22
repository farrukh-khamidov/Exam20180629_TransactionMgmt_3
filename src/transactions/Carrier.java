package transactions;

import java.util.HashSet;
import java.util.Set;

public class Carrier {

    private String name;
    private Set<Region> regions = new HashSet<>();

    public Carrier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void addRegion(Region region) {
        regions.add(region);
    }
}
