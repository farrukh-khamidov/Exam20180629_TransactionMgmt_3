package transactions;

import java.util.HashSet;
import java.util.Set;

public class Region {
    private String name;
    private Set<Place> places = new HashSet<>();

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addPlace(Place place) {
        places.add(place);
    }

    public Set<Place> getPlaces() {
        return places;
    }
}
