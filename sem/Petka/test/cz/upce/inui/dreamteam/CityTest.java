package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.service.CityFactory;
import cz.upce.inui.dreamteam.state.City;
import cz.upce.inui.dreamteam.state.Station;
import cz.upce.inui.dreamteam.state.Street;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class CityTest {
    @Test
    public void cityInstantiation() {
        new City();
    }

    @Test
    public void cityStations() {
        City city = new City();
        Set<Station> stations = city.getStations();
        Assert.assertNotNull(stations);
        Assert.assertTrue(stations.isEmpty());
    }

    @Test
    public void cityStreets() {
        City city = new City();
        Set<Street> streets = city.getStreets();
        Assert.assertNotNull(streets);
        Assert.assertTrue(streets.isEmpty());
    }

    @Test
    public void cityIsWholeCityGuarded() {
        City emptyCity = CityFactory.instance().getEmptyCity();
        Assert.assertFalse(emptyCity.isWholeCityGuarded());
        emptyCity.guardStation("B");
        Assert.assertFalse(emptyCity.isWholeCityGuarded());
        emptyCity.guardStation("E");
        Assert.assertFalse(emptyCity.isWholeCityGuarded());
        emptyCity.guardStation("H");
        Assert.assertTrue(emptyCity.isWholeCityGuarded());
    }

    @Test
    public void cityGuardStation() {
        City emptyCity = CityFactory.instance().getEmptyCity();
        emptyCity.guardStation("A");

        for (Station station : emptyCity.getStations()) {
            if (station.getCode().equals("A")) {
                Assert.assertTrue(station.isGuarded());
            } else {
                Assert.assertFalse(station.isGuarded());
            }
        }

        emptyCity.guardStation("B");
        for (Station station : emptyCity.getStations()) {
            String code = station.getCode();
            if (code.equals("A") || code.equals("B")) {
                Assert.assertTrue(station.isGuarded());
            } else {
                Assert.assertFalse(station.isGuarded());
            }
        }

    }

    @Test
    public void cityToString() {
        City city = CityFactory.instance().getEmptyCity();
        String toString = city.toString();
        Assert.assertNotNull(toString);
    }

    @Test
    public void cityLeaveStation() {
        City city = CityFactory.instance().getEmptyCity();
        Station station = city.getStation("A");
        Assert.assertFalse(station.isGuarded());
        city.guardStation(station);
        Assert.assertTrue(station.isGuarded());
        city.leaveStation(station);
        Assert.assertFalse(station.isGuarded());
    }
}
