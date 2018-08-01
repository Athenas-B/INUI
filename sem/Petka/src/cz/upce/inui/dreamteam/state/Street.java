package cz.upce.inui.dreamteam.state;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent single street in city that is connected by stations.
 */
public class Street {
    private final Set<Station> stations;
    private final Station firstEnd;
    private final Station secondEnd;

    /**
     * <p>
     * Creates new street containing given stations.
     * </p>
     * <p>
     * The stations on ends of street are defined separately in order to be able to define ends of street itself. Any other station that is lying between ends may be defined using vararg argument.
     * </p>
     *
     * @param firstEnd  First end of street
     * @param secondEnd Second end of street
     * @param stations  Additional stations between first and second street
     * @throws IllegalArgumentException If any of argument value is <code>null</code>.
     */
    public Street(Station firstEnd, Station secondEnd, Station... stations) {
        if (firstEnd == null || secondEnd == null) {
            throw new IllegalArgumentException("Street has to contain at least 2 stations");
        }
        Set<Station> newStations = new HashSet<>(Arrays.asList(stations));
        newStations.add(firstEnd);
        newStations.add(secondEnd);
        if (newStations.contains(null)) {
            throw new IllegalArgumentException("Stations cannot contain null");
        }
        this.stations = newStations;
        this.firstEnd = firstEnd;
        this.secondEnd = secondEnd;
    }

    public boolean containsStation(Station station) {
        return stations.contains(station);
    }

    public boolean isGuarded() {
        return stations.stream().anyMatch(Station::isGuarded);
    }

    public Position getFirstEnd() {
        return firstEnd.getPosition();
    }

    public Position getSecondEnd() {
        return secondEnd.getPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return !(stations != null ? !stations.equals(street.stations) : street.stations != null);
    }

    @Override
    public int hashCode() {
        return stations != null ? stations.hashCode() : 0;
    }

    @Override
    public String toString() {
        return stations.toString();
    }
}
