package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;

/**
 * Helper data structure. It stores all informations of a part/cell of the tetromino.
 */
public class TetrominoPart {
    /**
     *
     */
    private final SquareView squareView;
    /**
     *
     */
    private final int offsetX;
    /**
     *
     */
    private final int offsetY;
    /**
     *
     * @param square The view of this part.
     * @param x X offset (distance from the tetromino's X coordinate).
     * @param y Y offset (distance from the tetromino's Y coordinate).
     */
    public TetrominoPart(SquareView square, int x, int y) {
        squareView = square;
        offsetX = x;
        offsetY = y;
    }
    /**
     *
     * @return
     */
    public SquareView getView() {
        return squareView;
    }
    /**
     *
     * @return
     */
    public int getOffsetX() {
        return offsetX;
    }
    /**
     *
     * @return
     */
    public int getOffsetY() {
        return offsetY;
    }
}