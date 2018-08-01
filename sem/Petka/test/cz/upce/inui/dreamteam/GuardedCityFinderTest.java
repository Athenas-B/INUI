package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.service.GuardedCityFinder;
import cz.upce.inui.dreamteam.state.City;
import org.junit.Assert;
import org.junit.Test;

public class GuardedCityFinderTest {

    @Test
    public void findGuardedCity() {
        City guardedCity = new GuardedCityFinder().findGuardedCity();
        Assert.assertNotNull(guardedCity);
        Assert.assertTrue(guardedCity.isWholeCityGuarded());
    }
}
