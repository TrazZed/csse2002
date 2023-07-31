package lms.logistics;

import lms.logistics.belts.Belt;
import lms.logistics.container.Producer;
import lms.logistics.container.Receiver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    private Path sameNode, path1, path2, path3, path4, path5, path6;
    private Item item1, item2, item3, item4;
    private Producer producer;
    private Belt belt1, belt2, belt3, belt4;
    private Receiver receiver;

    @Before
    public void setup() {
        item1 = new Item("ab");
        item2 = new Item("aa");
        item3 = new Item("ac");
        item4 = new Item("ba");
        producer = new Producer(1, item1);
        belt1 = new Belt(2);
        belt2 = new Belt(3);
        belt3 = new Belt(4);
        belt4 = new Belt(5);
        receiver = new Receiver(6, item1);
        path1 = new Path(producer);
        path2 = new Path(belt1);
        path3 = new Path(belt2);
        path4 = new Path(belt3);
        path5 = new Path(belt4);
        path6 = new Path(receiver);
        sameNode = new Path(belt1);

        path1.setNext(path2);
        path2.setPrevious(path1);
        path2.setNext(path3);
        path3.setPrevious(path2);
        path3.setNext(path4);
        path4.setPrevious(path3);
        path4.setNext(path5);
        path5.setPrevious(path4);
        path5.setNext(path6);
        path6.setPrevious(path5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullNodeConstructor() {
        Producer node = null;
        Path pathNull = new Path(node);
    }

    @Test (expected = NullPointerException.class)
    public void pathSetPreviousNull() {
        Path pathNull = new Path(new Belt(4));
        pathNull.setPrevious(null);
    }

    @Test (expected = NullPointerException.class)
    public void pathSetNextNull() {
        Path pathNull = new Path(new Belt(4));
        pathNull.setNext(null);
    }

    @Test
    public void pathConstructorNode() {
        Path newPath = new Path(producer);
        assertEquals(newPath.getNode(), producer);
        assertEquals(newPath.getPrevious(), null);
        assertEquals(newPath.getNext(), null);
    }

    @Test
    public void pathConstructorPath() {
        Path newPath = new Path(path3);
        assertEquals(newPath.getNode(), path3.getNode());
        assertEquals(newPath.getPrevious(), path3.getPrevious());
        assertEquals(newPath.getNext(), path3.getNext());
    }

    @Test
    public void pathConstructorPathAndNode() {
        Path newPath = new Path(belt1, path1, path3);
        assertEquals(newPath.getNode(), belt1);
        assertEquals(newPath.getPrevious(), path1);
        assertEquals(newPath.getNext(), path3);
    }

    @Test
    public void getNodeTest() {
        assertEquals(path1.getNode(), producer);
    }

    @Test
    public void getNodeIncorrect() {
        assertNotEquals(path2.getNode(), producer);
    }

    @Test
    public void setPreviousTest() {
        path3.setPrevious(path1);
        assertEquals(path3.getPrevious(), path1);
    }

    @Test
    public void setNextTest() {
        path3.setNext(path6);
        assertEquals(path3.getNext(), path6);
    }

    @Test
    public void setPreviousSymmetry() {
        path3.setPrevious(path2);
        assertEquals(path3.getPrevious(), path2);
        assertEquals(path2.getNext(), path3);
    }

    @Test
    public void setNextSymmetry() {
        path3.setNext(path4);
        assertEquals(path4.getPrevious(), path3);
        assertEquals(path3.getNext(), path4);
    }

    @Test
    public void getHeadPath() {
        assertEquals(path1, path2.head());
    }

    @Test
    public void getHeadPathSecond() {
        assertEquals(path1, path3.head());
        assertEquals(path1, path4.head());
        assertEquals(path1, path5.head());
        assertEquals(path1, path6.head());
    }

    @Test
    public void getHeadPathThird() {
        assertEquals(path1, path1.head());
    }

    @Test
    public void getHeadPathIncorrect() {
        assertNotEquals(path2, path3.head());
    }

    @Test
    public void getTailPath() {
        assertEquals(path6, path1.tail());
    }

    @Test
    public void getTailPathSecond() {
        assertEquals(path6, path2.tail());
        assertEquals(path6, path3.tail());
        assertEquals(path6, path4.tail());
        assertEquals(path6, path5.tail());
    }

    @Test
    public void getTailPathThird() {
        assertEquals(path6, path6.tail());
    }

    @Test
    public void getTailPathIncorrect() {
        assertNotEquals(path2, path3.tail());
    }

    @Test
    public void toStringTest() {
        String test = "START -> <Producer-1> -> <Belt-2> -> <Belt-3> -> <Belt-4> -> <Belt-5> -> <Receiver-6> -> END";
        assertEquals(path1.toString(), test);
        assertEquals(path3.toString(), test);
    }

    @Test
    public void pathsEqual() {
        assertEquals(sameNode.equals(path2), true);
    }

    @Test
    public void pathsNotEqual() {
        assertEquals(path1.equals(path2), false);
    }

    @Test
    public void getNextTest() {
        assertEquals(path1.getNext(), path2);
    }

    @Test
    public void getNextTestIncorrect() {
        assertNotEquals(path2.getNext(), path4);
    }
}
