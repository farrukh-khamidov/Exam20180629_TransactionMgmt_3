package transactions;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Region {
    private String name;
    private Set<Place> places = new TreeSet<>(Comparator.comparing(Place::getName));

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
