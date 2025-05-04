import org.junit.*;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
;


/**
 * Test class for MazeSolver.
 */
public class MazeSolverTest  {
    private MazeSolver maze;

    /**
     * This is the set up for the tests.
     */
    @Before
    public void setUp() {
        
        maze = new MazeSolver("m2x2.txt");
    }

    /**
     * Test if getSize() returns the size of the cell.
     */
    @Test
    public void testLoadMazeSize() {
        assertEquals(2, maze.getSize());
    }


    /**
     * Tests if the Grid is initalized correctly.
     */
    @Test
    public void testGridInitialization() {
        Maze.CELL[][] grid = maze.getGrid();
        assertNotNull(grid);
        assertEquals(2, grid.length);
        assertEquals(2, grid[0].length);
        // Check that the maze has open cells (1s)
        assertEquals(Maze.CELL.OPEN, grid[0][0]);
        assertEquals(Maze.CELL.OPEN, grid[1][1]);
    }

    /**
     * Test if postionIsValid() will return the correct boolean value.
     * 
     */
    @Test
    public void testPositionIsValid() {
        // Valid positions
        assertTrue(maze.positionIsValid(new Position(0, 0)));
        assertTrue(maze.positionIsValid(new Position(1, 1)));
        // Invalid positions
        assertFalse(maze.positionIsValid(new Position(-1, 0)));
        assertFalse(maze.positionIsValid(new Position(0, 2)));
    }

    /**
     * Test if postion is available works correctly.
     */
    @Test
    public void testPositionIsAvailable() {
        // Assuming open cells are available
        assertTrue(maze.positionIsAvailable(new Position(0, 0)));
        // Walls are not available
        // Let's set a wall and test
        maze.getGrid()[0][1] = Maze.CELL.WALL;
        assertFalse(maze.positionIsAvailable(new Position(0, 1)));
    }

    /**
     * test if the Marked as visted works.
     */
    @Test
    public void testMarkAsVisited() throws IllegalAccessException {
        Position p = new Position(0, 0);
        maze.markAsVisited(p);
        assertEquals(Maze.CELL.VISITED, maze.getGrid()[0][0]);
    }
    /**
     * test if tansvere() will throw an exception for null pointer.
     * @throws IllegalAccessException
     */
    @Test
    public void testTraverseNullPosition() throws IllegalAccessException {
        assertFalse(maze.traverse(null));
    }

    /**
     * Test if transverse returns false if start is wall.
     * @throws IllegalAccessException
     */
    @Test
    public void testTraverseStartIsWall() throws IllegalAccessException {
        Position start = new Position(0, 0);
        maze.getGrid()[0][0] = Maze.CELL.WALL;
        assertFalse(maze.traverse(start));
    }

    /**
     * Test if traverse returns false if start is visted.
     * @throws IllegalAccessException
     */
    @Test
    public void testTraverseStartIsVisited() throws IllegalAccessException {
        Position start = new Position(0, 0);
        maze.getGrid()[0][0] = Maze.CELL.VISITED;
        assertFalse(maze.traverse(start));
    }

    /**
     * Test if traverse returns trur if at start.
     * @throws IllegalAccessException
     */
    @Test
    public void testTraverseTargetIsStart() throws IllegalAccessException {
        Position start = new Position(1, 1);
        maze.setTargetPosition(start); // assuming setTarget is a method
        maze.getGrid()[1][1] = Maze.CELL.OPEN;
        assertTrue(maze.traverse(start));
        assertEquals(Maze.CELL.VISITED, maze.getGrid()[1][1]);
    }

    /**
     * Test the behavior of traverse.
     * @throws IllegalAccessException
     */
    @Test
    public void testTraverseFindsTargetThroughRecursion() throws IllegalAccessException {
        Position start = new Position(0, 0);
        maze.getGrid()[0][0] = Maze.CELL.OPEN;
        maze.getGrid()[0][1] = Maze.CELL.OPEN;
        maze.setTargetPosition(new Position(0, 1)); // assuming this sets the target
        assertTrue(maze.traverse(start));
    }

    /**
     * Test if Traverse returns false if  there is no path to target.
     * @throws IllegalAccessException
     */
    @Test
    public void testTraverseNoPathToTarget() throws IllegalAccessException {
        Position start = new Position(0, 0);
        maze.getGrid()[0][0] = Maze.CELL.OPEN;
        maze.getGrid()[0][1] = Maze.CELL.WALL;
        maze.getGrid()[1][0] = Maze.CELL.WALL;
        maze.getGrid()[0][0] = Maze.CELL.OPEN;
        maze.setTargetPosition(new Position(1, 1));
        assertFalse(maze.traverse(start));
    }


    /**
     * tests the behavior of PickUpGoldCoins.
     * @throws IllegalAccessException
     */
    @Test
    public void testPickupGoldCoins() throws IllegalAccessException {
       
        MazeSolver mazeWithCoins = new MazeSolver("mazeg.txt");
        Position start = mazeWithCoins.getStartPosition();
        int coins = mazeWithCoins.pickupGoldCoins(start);
        
        assertTrue(coins >= 0);
        
        for (int i = 0; i < mazeWithCoins.getSize(); i++) {
            for (int j = 0; j < mazeWithCoins.getSize(); j++) {
                Maze.CELL cell = mazeWithCoins.getGrid()[i][j];
                assertTrue(cell == Maze.CELL.OPEN || cell == Maze.CELL.VISITED);
            }
        }
    }
    /**
     * Tests the behavoir of PostionIsTarget.
     */
    @Test
    public void testPositionIsTarget() {
        
        maze.setTargetPosition(new Position(1, 1));
        assertTrue(maze.positionIsTarget(new Position(1, 1)));
        assertFalse(maze.positionIsTarget(new Position(0, 0)));
    }
    /**
     * Testis if the FileNotfoundException is throw in the constructor 
     * if there is no file or invalid file.
     */
    @Test
    public void testFileNotFoundExceptionInConstructor() {
        
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        String fakeFileName = "nonexistent_file.txt";
        new MazeSolver(fakeFileName);

    
        System.setErr(System.err);

        String expectedMessage = "File not found: " + fakeFileName;
        assertTrue(errContent.toString().contains(expectedMessage));
    }


    /**
     * Test if the Exception is throw for MarkAsVisited.
     */
    @Test
    public void testMarkAsVisitedInvalidPositionThrowsException() {
        MazeSolver maze = new MazeSolver("maze1.txt");
        Position invalidPos = new Position(-1, -1); // invalid position

        try {
            maze.markAsVisited(invalidPos);
            fail("Expected IllegalAccessException");
        } catch (IllegalAccessException e) {
            assertEquals("Invalid position: " + invalidPos, e.getMessage());
        }
    }

    /**
     * Tests that markAsVisited throws an IllegalAccessException when given an invalid position.
     */
    @Test
    public void testMarkAsPathInvalidPositionThrowsException() {
        MazeSolver maze = new MazeSolver("maze1.txt");
        Position invalidPos = new Position(100, 100); // out of bounds
        try {
            maze.markAsPath(invalidPos);
            fail("Expected IllegalAccessException");
        } catch (IllegalAccessException e) {
            assertEquals("Invalid position: " + invalidPos, e.getMessage());
        }
    }
    /**
     * Tests that markAsPath correctly marks a valid position as part of the path.
     *
     * @throws IllegalAccessException if the position is unexpectedly considered invalid.
     */
    @Test
    public void testMarkAsPathValidPositionSetsPath() throws IllegalAccessException {
        MazeSolver maze = new MazeSolver("maze1.txt");
        Position validPos = new Position(0, 0); // assuming (0,0) is valid
    
        maze.markAsPath(validPos);
        assertEquals(MazeSolver.CELL.PATH, maze.getGrid()[0][0]);
    }

    /** 
     *Test the boundary conditions of mark as path.
     */
    @Test
    public void testMarkAsPathBoundaryPosition() throws IllegalAccessException {
        MazeSolver maze = new MazeSolver("maze1.txt");
        int numRows = maze.getGrid().length; 
        int numCols = maze.getGrid()[0].length; 
    
        Position boundaryPos = new Position(numRows - 1, numCols - 1); 
        maze.markAsPath(boundaryPos);
    
    
        assertEquals(MazeSolver.CELL.PATH, 
        maze.getGrid()[boundaryPos.getRow()][boundaryPos.getCol()]);
}

    /**
     * Test if pickUpGoldCoins() behaves properly.
     * @throws IllegalAccessException
     */
    @Test
    public void testPickupGoldCoinsInvalidPositionReturnsZero() throws IllegalAccessException {
        MazeSolver maze = new MazeSolver("maze1.txt");
        int coins = maze.pickupGoldCoins(new Position(-5, -5));
        assertEquals(0, coins);
    }


    /**
     * Tests that pickupGoldCoins returns 0 when there is no gold coin at the specified position.
     * @throws IllegalAccessException if the position is invalid or cannot be accessed.
     */
    @Test
    public void testPickupGoldCoinsNoGoldReturnsZero() throws IllegalAccessException {
        MazeSolver maze = new MazeSolver("maze1.txt");
    // Setup grid with no gold at position (0,0)
        Position pos = new Position(0, 0);
        maze.setGrid(new Maze.CELL[][] {
            { Maze.CELL.OPEN }
        });
        int coins = maze.pickupGoldCoins(pos);
        assertEquals(0, coins);
    }


    /**
     * Tests that traverse() returns false when the starting position is null.
     * @throws IllegalAccessException if called incorrectly or if any internal access check fails.
     */
    @Test
    public void testTraverseNullPositionReturnsFalse() throws IllegalAccessException {
        MazeSolver maze = new MazeSolver("maze1.txt");
        boolean result = maze.traverse(null);
        assertFalse(result);
    }




    /**
     * Tests positionHasGold() returns true when the position contains a gold coin.
     */
    @Test
    public void testPositionHasGoldReturnsTrue() {
        MazeSolver maze = new MazeSolver("maze0g.txt");
    
        Maze.CELL[][] grid = {
            { Maze.CELL.OPEN, Maze.CELL.OPEN },
            { Maze.CELL.OPEN, Maze.CELL.GOLDCOIN }
        };
        maze.setGrid(grid);
        Position goldPosition = new Position(1, 1);
        assertTrue(maze.positionHasGold(goldPosition));
    }


    /**
     * Tests positionHasGold() returns false when the position does not contain a gold coin.
     */
    @Test
    public void testPositionHasGoldReturnsFalse() {
        MazeSolver maze = new MazeSolver("m2x2.txt");
    
        Maze.CELL[][] grid = {
            { Maze.CELL.OPEN, Maze.CELL.GOLDCOIN },
            { Maze.CELL.OPEN, Maze.CELL.OPEN }
        };
        maze.setGrid(grid);
        Position noGoldPosition = new Position(0, 0);
        assertFalse(maze.positionHasGold(noGoldPosition));
    }


    /**
     * Tests positionHasGold() returns false for an invalid position.
     */
    @Test
    public void testPositionHasGoldInvalidPosition() {
        MazeSolver maze = new MazeSolver("maze.txt");
    
        maze.setGrid(new Maze.CELL[][] {
            { Maze.CELL.OPEN }
        });
        Position invalidPosition = new Position(-1, -1);
        assertFalse(maze.positionHasGold(invalidPosition));
    }




}