package lms.grid;

import lms.logistics.belts.Belt;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class GameGridTest {

    private GameGrid emptyGame;
    private int range;

    @Before
    public void setup() {
        range = 3;
        emptyGame = new GameGrid(range);
    }

    @Test
    public void getRangeTest() {
        assertEquals(range, emptyGame.getRange());
    }

    @Test
    public void getCopyShallowTest() {
        Map<Coordinate, GridComponent> copy = emptyGame.getGrid();
        copy.put(new Coordinate(0, 0, 0), new Belt(1));
        assertNotEquals(copy, emptyGame.getGrid());
    }

    @Test
    public void getCopyShallowTest2() {
        Map<Coordinate, GridComponent> copy = emptyGame.getGrid();
        copy.put(new Coordinate(0,0,0), null);
        assertNotEquals(copy, emptyGame.getGrid());
    }

    @Test
    public void getCopyShallowTest3() {
        Map<Coordinate, GridComponent> copy = emptyGame.getGrid();
        Coordinate coord = new Coordinate(0,0,0);
        copy.put(coord, null);
        copy.remove(coord);
        assertNotEquals(copy, emptyGame.getGrid());
    }

    @Test
    public void getCopyShallowTest4() {
        Map<Coordinate, GridComponent> copy = emptyGame.getGrid();
        assertEquals(copy, emptyGame.getGrid());
    }

    @Test
    public void setCoordinateTest() {
        Coordinate coord = new Coordinate(0,0,0);
        Belt belt = new Belt(1);
        emptyGame.setCoordinate(coord, belt);
        Map<Coordinate, GridComponent> copy = emptyGame.getGrid();
        assertEquals(copy.get(coord), belt);
    }

    @Test
    public void overrideCoordinateTest() {
        Coordinate coord = new Coordinate(0,0,0);
        Belt belt = new Belt(1);
        Belt belt2 = new Belt(2);
        emptyGame.setCoordinate(coord, belt);
        emptyGame.setCoordinate(coord, belt2);
        Map<Coordinate, GridComponent> copy = emptyGame.getGrid();
        assertEquals(copy.get(coord), belt2);
    }

    @Test
    public void equalGameGrids() {
        GameGrid emptyGame2 = new GameGrid(range);
        assertNotEquals(emptyGame, emptyGame2);
    }

    @Test
    public void equalGameGrids2() {
        GameGrid emptyGame2 = new GameGrid(range);
        emptyGame.setCoordinate(new Coordinate(0,0,0), new Belt(1));
        emptyGame2.setCoordinate(new Coordinate(0,0,0), new Belt(1));
        assertNotEquals(emptyGame, emptyGame2);
    }

    @Test
    public void immutableGameGrids() {
        GameGrid emptyGame2 = emptyGame;
        emptyGame.setCoordinate(new Coordinate(0,0,0), new Belt(1));
        assertEquals(emptyGame, emptyGame2);
    }
}
