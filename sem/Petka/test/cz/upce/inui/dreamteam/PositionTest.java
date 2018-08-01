package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.state.Position;
import org.junit.Assert;
import org.junit.Test;

public class PositionTest {
    @Test
    public void positionInstantiation() {
        new Position(0, 0);
    }

    @Test
    public void positionCoordinates() {
        Position position = new Position(13, 17);
        Assert.assertEquals(13, position.getX());
        Assert.assertEquals(17, position.getY());
    }
}
