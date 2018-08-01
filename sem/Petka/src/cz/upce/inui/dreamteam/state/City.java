package cz.upce.inui.dreamteam.state;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains the whole state of all stations and streets.
 */
public class City {

    private final Set<Station> stations = new HashSet<>();
    private final Set<Street> streets = new HashSet<>();

    public boolean isWholeCityGuarded() {
        return streets.equals(getGuardedStreets());
    }

    public void guardStation(String stationCode) {
        getStationStream(stationCode).forEach(station -> station.setGuarded(true));
    }

    public void guardStation(Station stationToGuard) {
        getStationStream(stationToGuard).forEach(station -> station.setGuarded(true));
    }

    public void leaveStation(Station station) {
        getStationStream(station).forEach(stationToLeave -> stationToLeave.setGuarded(false));
    }

    public Station getStation(String stationCode) {
        return getStationStream(stationCode).findFirst().get();
    }

    public Set<Station> getStations() {
        return stations;
    }

    public Set<Street> getStreets() {
        return streets;
    }

    private Set<Street> getGuardedStreets() {
        Set<Station> guardedStations = stations.stream().filter(Station::isGuarded).collect(Collectors.toSet());
        Set<Street> guardedStreets = new HashSet<>();
        guardedStations.forEach((station) -> {
            guardedStreets.addAll(streets.stream().filter(street -> street.containsStation(station)).collect(Collectors.toSet()));
        });
        return guardedStreets;
    }

    private Stream<Station> getStationStream(Station stationToGuard) {
        return stations.stream().filter(station -> station.equals(stationToGuard));
    }

    private Stream<Station> getStationStream(String stationCode) {
        return stations.stream().filter(station -> station.getCode().equals(stationCode));
    }

    @Override
    public String toString() {
        return "City{"
                + "stations=" + stations
                + ", streets=" + streets
                + '}';
    }
}
