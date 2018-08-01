package cz.upce.inui.dreamteam.state;

/**
 * Represents position of element.
 * This class is immutable, meaning that once the instance is created, the state of an instance cannot be changed.
 */
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
