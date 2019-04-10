package hu.klenium.tetris.logic.board;

/**
 * Represents a cell in the board which is (among others) empty
 * (ie. doesn't store a tetromino part).
 */
public class EmptyBoardCell implements BoardCell {
    /**
     * This kind of cell must be empty, so it returns true.
     * @return True.
     */
    public boolean isEmpty() {
        return true;
    }
}