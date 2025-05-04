
/**
TODO Write a one-sentence summary of your  here.
TODO Follow it with additional details about its purpose, what abstraction
it represents, and how to use it.

@author  @TODO Put Your Name in the author tag
@version Apr 22, 2025
*/
public class Position {
    private int row;
    private int col;

    /**
     * Constructs a Position object with the specified row and column.
     *
     * @param row the row index of the position
     * @param col the column index of the position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row index of the position.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of the position.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Compares this position to the specified object for equality.
     * Two positions are equal if they have the same row and column values.
     *
     * @param arg the object to compare with
     * @return true if the positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object arg) {
        if (arg instanceof Position) {
            Position other = (Position) arg;
            return this.row == other.row && this.col == other.col;
        }
        return false;
    }

    /**
     * Returns a string representation of the position in the format (row, col).
     *
     * @return a string representing the position
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
