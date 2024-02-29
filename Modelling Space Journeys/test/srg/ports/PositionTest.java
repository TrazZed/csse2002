package srg.ports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    private Position origin;
    private Position destination;
    private Position negativeDestination;
    private Position positiveAndNegative;
    private Position positiveAndNegativeTwo;

    @Before
    public void setup() {
        origin = new Position(0, 0, 0);
        destination = new Position(5, 10, 15);
        negativeDestination = new Position(-4, -3, -2);
        positiveAndNegative = new Position(-7, -3, 4);
        positiveAndNegativeTwo = new Position(1, 7, -2);
    }

    @Test
    public void newPositionConstructor() {
        Position newPosition = new Position(1, 3, 6);
        assertEquals(newPosition.x, 1);
        assertEquals(newPosition.y, 3);
        assertEquals(newPosition.z, 6);
    }

    @Test
    public void destinationToOrigin() {
        assertEquals(origin.distanceTo(destination), 18);
    }

    @Test
    public void negativeToOrigin() {
        assertEquals(origin.distanceTo(negativeDestination), 5);
    }

    @Test
    public void positiveAndNegativeToOrigin() {
        assertEquals(origin.distanceTo(positiveAndNegative), 8);
    }

    @Test
    public void destinationToPositiveAndNegative() {
        assertEquals(destination.distanceTo(positiveAndNegative), 20);
    }

    @Test
    public void positiveAndNegativeToPositiveAndNegativeTwo() {
        assertEquals(positiveAndNegative.distanceTo(positiveAndNegativeTwo), 14);
    }

    @Test
    public void positiveAndNegativeTwoToOrigin() {
        assertEquals(origin.distanceTo(positiveAndNegativeTwo), 7);
    }

    @Test
    public void destinationToDestination() {
        assertEquals(destination.distanceTo(destination), 0);
    }

    @Test
    public void negativeDestinationToNegativeDestination() {
        assertEquals(negativeDestination.distanceTo(negativeDestination), 0);
    }

    @Test
    public void distanceToSymmetry() {
        assertEquals(origin.distanceTo(destination), destination.distanceTo(origin));
        assertEquals(positiveAndNegative.distanceTo(negativeDestination), negativeDestination.distanceTo(positiveAndNegative));
        assertEquals(destination.distanceTo(negativeDestination), negativeDestination.distanceTo(destination));
    }

    @Test
    public void destinationToString() {
        assertEquals(destination.toString(), "(5, 10, 15)");
    }

    @Test
    public void negativeToString() {
        assertEquals(negativeDestination.toString(), "(-4, -3, -2)");
    }

    @Test
    public void originToString() {
        assertEquals(origin.toString(), "(0, 0, 0)");
    }

    @Test
    public void positiveAndNegativeToString() {
        assertEquals(positiveAndNegative.toString(), "(-7, -3, 4)");
    }

    @Test
    public void positiveAndNegativeTwoToString() {
        assertEquals(positiveAndNegativeTwo.toString(), "(1, 7, -2)");
    }
}
