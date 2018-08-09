package hu.klenium.tetris.logic.board;

/**
 * Stores information about a cell that is needed by the board to work.
 */
public interface BoardCell {
    /**
     * Tells whether this cell is empty or not.
     * @return True when this cell doesn't contain a tetromino part,
     *         false otherwise.
     */
    boolean isEmpty();
}