package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.state.Position;
import cz.upce.inui.dreamteam.state.Station;
import cz.upce.inui.dreamteam.state.Street;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StreetTest {

    private Station a;
    private Station b;
    private Station c;

    @Before
    public void setup() {
        a = new Station("A", new Position(10, 20));
        b = new Station("B", new Position(30, 40));
        c = new Station("C", new Position(50, 60));
    }

    @Test
    public void streetInstantiation() {
        new Street(a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void streetNullInstantiation() {
        new Street(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void streetNullInstantiation2() {
        new Street(a, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void streetNullInstantiation3() {
        new Street(null, a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void streetNullInstantiation4() {
        new Street(a, b, b, null, b);
    }

    @Test
    public void streetEquals() {
        Street street1 = new Street(a, b);
        Street street2 = new Street(a, b);
        Street street3 = new Street(b, a);
        Assert.assertEquals(street1, street2);
        Assert.assertEquals(street1, street3);
        Assert.assertEquals(street2, street3);

        Street differentStreet = new Street(a, new Station("C", new Position(0, 0)));
        Assert.assertNotEquals(street1, differentStreet);
        Assert.assertNotEquals(street2, differentStreet);
        Assert.assertNotEquals(street3, differentStreet);
        differentStreet = new Street(new Station("C", new Position(0, 0)), a);
        Assert.assertNotEquals(street1, differentStreet);
        Assert.assertNotEquals(street2, differentStreet);
        Assert.assertNotEquals(street3, differentStreet);
    }

    @Test
    public void streetHashcode() {
        Street street1 = new Street(a, b);
        Street street2 = new Street(a, b);
        Street street3 = new Street(b, a);
        Assert.assertEquals(street1.hashCode(), street2.hashCode());
        Assert.assertEquals(street1.hashCode(), street3.hashCode());
        Assert.assertEquals(street2.hashCode(), street3.hashCode());

        Street differentStreet = new Street(a, new Station("C", new Position(0, 0)));
        Assert.assertNotEquals(street1.hashCode(), differentStreet.hashCode());
        Assert.assertNotEquals(street2.hashCode(), differentStreet.hashCode());
        Assert.assertNotEquals(street3.hashCode(), differentStreet.hashCode());
        differentStreet = new Street(new Station("C", new Position(0, 0)), a);
        Assert.assertNotEquals(street1.hashCode(), differentStreet.hashCode());
        Assert.assertNotEquals(street2.hashCode(), differentStreet.hashCode());
        Assert.assertNotEquals(street3.hashCode(), differentStreet.hashCode());
    }

    @Test
    public void streetContains() {
        Street street1 = new Street(a, b);
        Assert.assertTrue(street1.containsStation(a));
        Assert.assertTrue(street1.containsStation(b));
        Assert.assertFalse(street1.containsStation(null));
        Assert.assertFalse(street1.containsStation(new Station("C", new Position(0, 0))));
    }

    @Test
    public void streetToString() {
        Street street = new Street(a, b);
        String toString = street.toString();
        Assert.assertNotNull(toString);
    }

    @Test
    public void streetGetEnds() {
        Street street = new Street(a, b);
        Assert.assertEquals(a.getPosition(), street.getFirstEnd());
        Assert.assertEquals(b.getPosition(), street.getSecondEnd());
        street = new Street(a, c, b);
        Assert.assertEquals(a.getPosition(), street.getFirstEnd());
        Assert.assertEquals(c.getPosition(), street.getSecondEnd());
    }

    @Test
    public void streetIsGuarded() {
        Street street = new Street(a, b);
        Assert.assertFalse(street.isGuarded());
        a.setGuarded(true);
        Assert.assertTrue(street.isGuarded());
        a.setGuarded(false);
        Assert.assertFalse(street.isGuarded());
        b.setGuarded(true);
        Assert.assertTrue(street.isGuarded());
    }
}
