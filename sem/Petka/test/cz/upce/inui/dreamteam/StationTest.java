package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.state.Position;
import cz.upce.inui.dreamteam.state.Station;
import org.junit.Assert;
import org.junit.Test;

public class StationTest {
    @Test
    public void stationInstantiation() {
        new Station("code", new Position(0, 0));
    }

    @Test(expected = NullPointerException.class)
    public void nullInstantiation() {
        new Station(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullInstantiation2() {
        new Station("A", null);
    }

    @Test(expected = NullPointerException.class)
    public void nullInstantiation3() {
        new Station(null, new Position(0, 0));
    }

    @Test
    public void stationCode() {
        Station station = new Station("A", new Position(0, 0));
        Assert.assertEquals("A", station.getCode());
    }

    @Test
    public void stationOccupied() {
        Station station = new Station("A", new Position(0, 0));
        Assert.assertFalse(station.isGuarded());
        station.setGuarded(true);
        Assert.assertTrue(station.isGuarded());
        station.setGuarded(false);
        Assert.assertFalse(station.isGuarded());
    }

    @Test
    public void stationPosition() {
        Station station = new Station("A", new Position(1, 2));
        Position position = station.getPosition();
        Assert.assertNotNull(position);
        Assert.assertEquals(1, position.getX());
        Assert.assertEquals(2, position.getY());
    }

    @Test
    public void stationEquals() {
        Station station = new Station("A", new Position(1, 2));
        Station station2 = new Station("A", new Position(1, 2));
        Station station3 = new Station("B", new Position(1, 2));
        Assert.assertEquals(station, station);
        Assert.assertEquals(station, station2);
        Assert.assertNotEquals(station, station3);
    }

    @Test
    public void stationHashcode() {
        Station station = new Station("A", new Position(1, 2));
        Station station2 = new Station("A", new Position(1, 2));
        Station station3 = new Station("B", new Position(1, 2));
        Assert.assertEquals(station.hashCode(), station.hashCode());
        Assert.assertEquals(station.hashCode(), station2.hashCode());
        Assert.assertNotEquals(station.hashCode(), station3.hashCode());
    }

    @Test
    public void stationToString() {
        Station station = new Station("A", new Position(1, 2));
        String toString = station.toString();
        Assert.assertNotNull(toString);
    }
}
