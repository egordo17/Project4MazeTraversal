import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
import java.util.Scanner;

/**
 * Maze interface. Defines methods to
 * traverse and solve a maze.
 * 
 * It will be there already waiting for
 * your submission.
 */
public abstract class Maze {
    /**
     * enum CELL definition that captures all the possible
     * values in the grid.
     */
    public enum CELL { WALL, OPEN, VISITED, PATH, GOLDCOIN };
                /* 0,    1,    2,     3,    4 */
    

    /**
     * Reads a file in and stores the grid for
     * the maze based on the content of the file.
     * The file contains the size of the maze,
     * the start position for the 
     * 
     * @param inFile name of the file
     */
    public void readFile(String inFile) throws FileNotFoundException
    {
        String line;
        int size = 5;
        Position start = new Position(0,0);
        Position target = new Position(size-1, size-1);
        CELL[][] g = null;

        File myObj = new File(inFile);
        Scanner scan = new Scanner(myObj);

        while (scan.hasNext()) {
            line = scan.nextLine();
            if (line.equalsIgnoreCase("size")) {
                size = scan.nextInt();
                target = new Position(size-1, size-1);
            }
            else if (line.equalsIgnoreCase("grid")) {
                g = new MazeSolver.CELL[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        String s = scan.next();
                        if (s.equalsIgnoreCase("S")) {
                            g[i][j] = MazeSolver.CELL.OPEN;
                            start = new Position(i, j);
                        }
                        else if (s.equalsIgnoreCase("T")) {
                            g[i][j] = MazeSolver.CELL.OPEN;
                            target = new Position(i, j);
                        }
                        else if (s.equalsIgnoreCase("G")) {
                            g[i][j] = MazeSolver.CELL.GOLDCOIN;
                        }
                        else {
                            g[i][j] = MazeSolver.CELL.values()[Integer.parseInt(s)];
                        }
                    }
                }
            }
        }
        scan.close();
        setGrid(g); // might be null
        setStartPosition(start);    // might be 0,0
        setTargetPosition(target);
    }

    // All of the methods below need to be defined in the MazeSolver class

    /**
     * Returns the internal grid (2d array).
     * @return 2d grid with CELL values
     */
    public abstract CELL[][] getGrid();

    /**
     * Sets the internal grid (2d array).
     * @param g 2d grid
     */
    public abstract void setGrid(CELL[][] g);

    /**
     * Set the internal starting position for the traversals. 
     * This is the position passed to start the traversal of the map.
     * @param st position for the starting point of the traversal
     */
    public abstract void setStartPosition(Position st);

    /**
     * Return the internal starting position stored in this object.
     * @return start position
     */
    public abstract Position getStartPosition();

    /**
     * Set the internal target position for the traversal.
     * @param ed position for the target point of the traversal
     */
    public abstract void setTargetPosition(Position ed);

    /**
     * Return the internal target position stored in this object.
     * @return target position
     */
    public abstract Position getTargetPosition();

    /**
     * Return true if position p is the same (equal) as the target position.
     * Returns false otherwise.
     * @param pos position
     * @return true if position is the target position
     */
    public abstract boolean positionIsTarget(Position pos);

    /**
     * Return true if position op is valid. A valid position is one 
     * whose row is between 0 and numRows-1, and column is between 0 
     * and numCols-1.
     * @param pos position
     * @return true if position is valid
     */
    public abstract boolean positionIsValid(Position pos);

    /**
     * Return true if position p is valid (as defined by positionIsValid)
     * and the position is either OPEN or has a GOLDCOIN.
     * @param pos position
     * @return true if position is available
     */
    public abstract boolean positionIsAvailable(Position pos);

    /**
     * Return true if position p is valid (as defined by positionIsValid)
     *  and has a GOLDCOIN.
     * @param pos position
     * @return true if position has a gold coin 
     */
    public abstract boolean positionHasGold(Position pos);

    /**
     * Stores VISITED at position p in the internal grid. If p is not 
     * a valid position (as defined by positionIsValid), this should throw 
     * an exception (IllegalAccessException).
     * @param pos position
     */
    public abstract void markAsVisited(Position pos) throws IllegalAccessException;

    /**
     * Stores PATH at position p in the internal grid. If p is not a valid 
     * position (as defined by positionIsValid), this should throw an 
     * exception (IllegalAccessException).
     * @param pos position
     */
    public abstract void markAsPath(Position pos) throws IllegalAccessException;

    /**
     * Traverse the maze recursively starting at the start position and continuing
     * until it reaches the target position. As positions are explored, they should 
     * be marked as VISITED. Upon returning from the recursive calls, this routine 
     * should mark the positions along the path from start to target as PATH. This 
     * routine returns true if it finds a path from start to target, false otherwise.
     * @param pos position
     * @return true if it reaches the target position, false otherwise
     */
    public abstract boolean traverse(final Position pos) throws IllegalAccessException;

    /**
     * Traverse the maze recursively picking up coins available throughout the maze. 
     * This routine should traverse the full maze such that it collects all coins that 
     * are reachable. This routine returns the number of coins collected.
     * @param pos position
     * @return the number of gold coins picked up
     */
    public abstract int pickupGoldCoins(final Position pos) throws IllegalAccessException;


    // These are provided for you.
    /**
     * Returns the size of the grid, it assumes it is a
     * square grid.
     * @return lengths of the grid
     */
    public int getSize()
    {
        return getGrid().length;
    }

    /**
     * Routine simply calls traverse(getStartPosition()). See
     * that routine for documentation.
     * @return true if it reaches the target position, false otherwise
     */
    public boolean traverse() throws IllegalAccessException
    {
        return traverse(getStartPosition());
    }

    /**
     * Routine simply calls pickupGoldCoins(getStartPosition()). See
     * that routine for documentation.
     * @return the number of gold coins picked up
     */
    public int pickupGoldCoins() throws IllegalAccessException
    {
        return pickupGoldCoins(getStartPosition());
    }

    /**
     * Converts the board to a string representation that can
     * be printed.
     * @return a string containing a grid representaiton of the maze.
     */
    @Override
    public String toString()
    {
        CELL[][] g = getGrid();
        StringBuilder result = new StringBuilder("\n");
        for (int r = 0; r < getSize(); r++) {
            result.append("[");
            for (int c = 0; c < getSize(); c++) {
                if (g[r][c] == CELL.WALL) {
                    result.append(" + ");
                }
                else if (g[r][c] == CELL.OPEN) {
                    if (getStartPosition().equals(new Position(r,c))) {
                        result.append(" S ");   // if start use S, if end use T
                    }
                    else if (getTargetPosition().equals(new Position(r,c))) {
                        result.append(" T ");   // if end use T
                    }
                    else {
                        result.append("   ");
                    }
                }
                else if (g[r][c] == CELL.VISITED) {
                    result.append(" x ");
                }
                else if (g[r][c] == CELL.PATH) {
                    result.append(" √ ");
                }
                else if (g[r][c] == CELL.GOLDCOIN) {
                    result.append(" G ");
                }
            }
            result.append("]\n");
        }
        return result.toString();
    }
}
/*
 * Copyright: This programming assignment specification and the provided sample
 * code are protected by copyright. The professor is the exclusive owner of
 * copyright of this material. You are encouraged to take notes and make copies
 * of the specification and the source code for your own educational use.
 * However, you may not, nor may you knowingly allow others to reproduce or
 * distribute the materials publicly without the express written consent of the
 * professor. This includes providing materials to commercial course material
 * suppliers such as CourseHero and other similar services. Students who
 * publicly distribute or display or help others publicly distribute or display
 * copies or modified copies of this material may be in violation of University
 * Policy 406, The Code of Student Responsibility
 * https://legal.charlotte.edu/policies/up-406.
 */
