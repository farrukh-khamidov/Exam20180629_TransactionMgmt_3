package transactions;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Carrier {

    private String name;
    private Set<Region> regions = new TreeSet<>(Comparator.comparing(Region::getName));

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
