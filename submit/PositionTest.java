import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test class for Position.
 */
public class PositionTest {
    
    /**
     * Tests the constructor and toString method.
     */
    @Test
    public void testConstructorAndToString() {
        Position pos = new Position(3, 4);
        assertEquals(3, pos.getRow());
        assertEquals(4, pos.getCol());
        assertEquals("(3, 4)", pos.toString());
    }

    /**
     * Tests the equals method when positions are the same.
     */
    @Test
    public void testEqualsSamePosition() {
        Position pos1 = new Position(5, 6);
        Position pos2 = new Position(5, 6);
        assertTrue(pos1.equals(pos2));
    }

    /**
     * Tests the equals method when positions are different.
     */
    @Test
    public void testEqualsDifferentPosition() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(2, 1);
        assertFalse(pos1.equals(pos2));
    }

    /**
     * Tests the equals method when copmared with a non-Position object.
     */
    @Test
    public void testEqualsDifferentObject() {
        Position pos = new Position(0, 0);
        String notAPosition = "Not a position";
        assertFalse(pos.equals(notAPosition));
    }

    /**
     * Tests that two positions with same row and column are equal.
     */
    @Test
    public void testEqualitySymmetry() {
        Position posA = new Position(7, 8);
        Position posB = new Position(7, 8);
        assertTrue(posA.equals(posB));
        assertTrue(posB.equals(posA));
    }

    /**
     * Tests the toString method for correct string format.
     */
    @Test
    public void testToStringFormat() {
        Position pos = new Position(9, 10);
        assertEquals("(9, 10)", pos.toString());
    }

    /**
     * Tests getRow and getCol methods.
     */
    @Test
    public void testGetters() {
        Position pos = new Position(11, 12);
        assertEquals(11, pos.getRow());
        assertEquals(12, pos.getCol());
    }

   

}