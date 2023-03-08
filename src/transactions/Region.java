package transactions;

import java.util.Set;
import java.util.TreeSet;

public class Region {
    private String name;
    private Set<String> places = new TreeSet<>();

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addPlace(String placeName) {
        places.add(placeName);
    }

    public Set<String> getPlaces() {
        return places;
    }
}
