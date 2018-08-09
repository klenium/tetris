package hu.klenium.tetris.logic.board;

/**
 * Represents a cell in the board which is (among others) used
 * (ie. stores a tetromino part).
 */
public class FilledBoardCell implements BoardCell {
    /**
     * The stored tetromino part's type.
     */
    private final int type;
    /**
     * Creates a cell to store the information of a landed tetromino part.
     * @param type The tetromino's type.
     */
    public FilledBoardCell(int type) {
        this.type = type;
    }
    /**
     * Tells which kind of tetromino part is stored inside this cell.
     * @return The tetromino part's type.
     */
    public int getType() {
        return type;
    }
    /**
     * This kind of cell must be non-empty, so it returns false.
     * @return False.
     */
    public boolean isEmpty() {
        return false;
    }
}