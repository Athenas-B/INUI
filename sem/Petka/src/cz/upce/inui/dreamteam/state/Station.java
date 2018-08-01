package cz.upce.inui.dreamteam.state;

import java.util.Objects;

/**
 * Represents station in the city.
 * Station has fixed code and position. It also holds information about whether the station is currently guarded or not.
 */
public class Station {
    private final String code;
    private final Position position;
    private boolean guarded;

    /**
     * Creates new station with fixed code and position.
     *
     * @param code     Code of the station
     * @param position Position of the station
     */
    public Station(String code, Position position) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(position);
        this.position = position;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isGuarded() {
        return guarded;
    }

    public void setGuarded(boolean guarded) {
        this.guarded = guarded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return !(code != null ? !code.equals(station.code) : station.code != null);

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return code;
    }
}
