package cz.upce.inui.dreamteam.service;

import cz.upce.inui.dreamteam.state.City;
import cz.upce.inui.dreamteam.state.Position;
import cz.upce.inui.dreamteam.state.Station;
import cz.upce.inui.dreamteam.state.Street;

import java.util.Set;

/**
 * Singleton that creates instances of {@link City}.
 */
public class CityFactory {

    private CityFactory() {
    }

    private static final CityFactory INSTANCE = new CityFactory();

    /**
     * Retrieves singleton instance of {@link CityFactory}.
     *
     * @return singleton instance
     */
    public static CityFactory instance() {
        return INSTANCE;
    }

    /**
     * Creates empty city that has defined structure as in assignment picture.
     *
     * @return New empty city.
     */
    public City getEmptyCity() {
        City result = new City();
        //Stations initialization
        Station a = new Station("A", new Position(430, 30));
        Station b = new Station("B", new Position(170, 140));
        Station c = new Station("C", new Position(250, 190));
        Station d = new Station("D", new Position(20, 200));
        Station e = new Station("E", new Position(310, 220));
        Station f = new Station("F", new Position(190, 245));
        Station g = new Station("G", new Position(50, 290));
        Station h = new Station("H", new Position(80, 370));
        Station i = new Station("I", new Position(215, 370));
        Station j = new Station("J", new Position(570, 370));
        Set<Station> stations = result.getStations();
        stations.add(a);
        stations.add(b);
        stations.add(c);
        stations.add(d);
        stations.add(e);
        stations.add(f);
        stations.add(g);
        stations.add(h);
        stations.add(i);
        stations.add(j);

        //Streets initialization
        Set<Street> streets = result.getStreets();
        streets.add(new Street(a, d, b));
        streets.add(new Street(a, h, f, c));
        streets.add(new Street(a, i, e));
        streets.add(new Street(b, j, e, c));
        streets.add(new Street(b, i, f));
        streets.add(new Street(d, h, g));
        streets.add(new Street(e, g, f));
        streets.add(new Street(h, j, i));

        return result;
    }

    /**
     * Creates city that has defined structure as in assignment picture and has "A", "B" and "C" stations guarded.
     *
     * @return New city with "A", "B" and "C" stations guarded.
     */
    public City getGuardedCity() {
        City city = getEmptyCity();
        city.guardStation("A");
        city.guardStation("B");
        city.guardStation("C");
        return city;
    }
}
