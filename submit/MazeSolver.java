import java.io.FileNotFoundException;

/**
 * MazeSolver is a class that extends Maze to provide
 * functionality for solving a maze and collecting gold coins.
 */
public class MazeSolver extends Maze {
    private CELL[][] grid;
    private Position startPosition;
    private Position targetPosition;

    /**
     * Constructs a MazeSolver by reading the maze from the specified input file.
     *
     * @param inputFile the name of the file containing the maze
     */
    public MazeSolver(String inputFile) {
        try {
            readFile(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFile);
        }
    }

    /**
     * Returns the maze grid.
     *
     * @return the 2D array of CELL representing the maze
     */
    @Override
    public CELL[][] getGrid() {
        return this.grid;
    }

    /**
     * Sets the maze grid.
     *
     * @param g the 2D array of CELL to set as the grid
     */
    @Override
    public void setGrid(CELL[][] g) {
        this.grid = g;
    }

    /**
     * Sets the starting position in the maze.
     *
     * @param p the Position to set as the starting point
     */
    @Override
    public void setStartPosition(Position p) {
        this.startPosition = p;
    }

    /**
     * Returns the starting position in the maze.
     *
     * @return the starting Position
     */
    @Override
    public Position getStartPosition() {
        return this.startPosition;
    }

    /**
     * Sets the target (goal) position in the maze.
     *
     * @param p the Position to set as the target
     */
    @Override
    public void setTargetPosition(Position p) {
        this.targetPosition = p;
    }

    /**
     * Returns the target (goal) position in the maze.
     *
     * @return the target Position
     */
    @Override
    public Position getTargetPosition() {
        return this.targetPosition;
    }

    /**
     * Checks if the given position is the target position.
     *
     * @param p the Position to check
     * @return true if p is the target position, false otherwise
     */
    public boolean positionIsTarget(Position p) {
        return p.equals(getTargetPosition());
    }

    /**
     * Checks if the given position is within the bounds of the maze.
     *
     * @param p the Position to check
     * @return true if the position is within bounds, false otherwise
     */
    public boolean positionIsValid(Position p) {
        int numRows = getSize();
        int numCols = getSize();
        int row = p.getRow();
        int col = p.getCol();
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    /**
     * Checks if the position is available to traverse (either OPEN or GOLDCOIN).
     *
     * @param p the Position to check
     * @return true if the position is available, false otherwise
     */
    public boolean positionIsAvailable(Position p) {
        if (!positionIsValid(p)) return false;
        CELL cell = getGrid()[p.getRow()][p.getCol()];
        return cell == CELL.OPEN || cell == CELL.GOLDCOIN;
    }

    /**
     * Checks if the position contains a gold coin.
     *
     * @param p the Position to check
     * @return true if the position has a GOLDCOIN, false otherwise
     */
    public boolean positionHasGold(Position p) {
        if (!positionIsValid(p)) return false;
        return getGrid()[p.getRow()][p.getCol()] == CELL.GOLDCOIN;
    }

    /**
     * Marks the given position as VISITED in the grid.
     *
     * @param p the Position to mark as visited
     * @throws IllegalAccessException if the position is invalid
     */
    public void markAsVisited(Position p) throws IllegalAccessException {
        if (!positionIsValid(p)) {
            throw new IllegalAccessException("Invalid position: " + p);
        }
        getGrid()[p.getRow()][p.getCol()] = CELL.VISITED;
    }

    /**
     * Marks the given position as part of the PATH in the grid.
     *
     * @param p the Position to mark as part of the path
     * @throws IllegalAccessException if the position is invalid
     */
    public void markAsPath(Position p) throws IllegalAccessException {
        if (!positionIsValid(p)) {
            throw new IllegalAccessException("Invalid position: " + p);
        }
        getGrid()[p.getRow()][p.getCol()] = CELL.PATH;
    }

    /**
     * Recursively traverses the maze from the given position to find the target.
     *
     * @param p the starting Position for traversal
     * @return true if the target is found, false otherwise
     * @throws IllegalAccessException if an invalid position is encountered
     */
    @Override
    public boolean traverse(Position p) throws IllegalAccessException {
        if (p == null)
            return false;

        CELL currentCell = getGrid()[p.getRow()][p.getCol()];
        if (currentCell == CELL.WALL || currentCell == CELL.VISITED)
            return false;

        markAsVisited(p);
        if (positionIsTarget(p))
            return true;

        int[] dRows = {-1, 0, 1, 0};
        int[] dCols = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            Position nextPos = new Position(p.getRow() + dRows[i], p.getCol() + dCols[i]);
            if (positionIsValid(nextPos) && positionIsAvailable(nextPos)) {
                if (traverse(nextPos))
                    return true;
            }
        }

        return false;
    }

    /**
     * Recursively collects gold coins from the given position and marks visited cells.
     *
     * @param p the starting Position to begin collecting gold coins
     * @return the total number of gold coins collected
     * @throws IllegalAccessException if an invalid position is encountered
     */
    @Override
    public int pickupGoldCoins(Position p) throws IllegalAccessException {
        if (!positionIsValid(p))
            return 0;

        int coinsCollected = 0;
        CELL currentCell = getGrid()[p.getRow()][p.getCol()];

        if (currentCell == CELL.GOLDCOIN) {
            coinsCollected++;
            getGrid()[p.getRow()][p.getCol()] = CELL.OPEN;
        }

        markAsVisited(p);

        int[] dRows = {-1, 0, 1, 0};
        int[] dCols = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            Position nextPos = new Position(p.getRow() + dRows[i], p.getCol() + dCols[i]);
            if (positionIsValid(nextPos) &&
                (getGrid()[nextPos.getRow()][nextPos.getCol()] == CELL.OPEN ||
                 getGrid()[nextPos.getRow()][nextPos.getCol()] == CELL.GOLDCOIN)) {
                coinsCollected += pickupGoldCoins(nextPos);
            }
        }

        return coinsCollected;
    }
}