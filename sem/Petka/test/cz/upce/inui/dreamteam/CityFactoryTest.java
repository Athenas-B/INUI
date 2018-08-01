package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.service.CityFactory;
import cz.upce.inui.dreamteam.state.City;
import cz.upce.inui.dreamteam.state.Station;
import cz.upce.inui.dreamteam.state.Street;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class CityFactoryTest {
    @Test
    public void cityFactoryInstance() {
        CityFactory instance = CityFactory.instance();
        Assert.assertNotNull(instance);
        //it is singleton
        Assert.assertEquals(instance, CityFactory.instance());
    }

    @Test
    public void cityFactoryEmptyCity() {
        City emptyCity = CityFactory.instance().getEmptyCity();
        Set<Station> stations = emptyCity.getStations();
        Set<Street> streets = emptyCity.getStreets();
        Assert.assertNotNull(emptyCity);
        Assert.assertNotNull(stations);
        Assert.assertNotNull(streets);
        Assert.assertFalse(stations.isEmpty());
        Assert.assertFalse(streets.isEmpty());
    }
}
