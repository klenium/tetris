package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;

/**
 * Helper data structure that stores one tetromino part/cell's data.
 */
public class TetrominoPart {
    /**
     * The part's view, which displays it.
     */
    private final SquareView squareView;
    /**
     * X offset (distance from the tetromino's X coordinate).
     */
    private final int offsetX;
    /**
     * Y offset (distance from the tetromino's Y coordinate).
     */
    private final int offsetY;
    /**
     * Creates a new tetromino part.
     * @param view The view of this part.
     * @param x The part's X offset.
     * @param y The part's Y offset.
     */
    public TetrominoPart(SquareView view, int x, int y) {
        squareView = view;
        offsetX = x;
        offsetY = y;
    }
    public SquareView getView() {
        return squareView;
    }
    public int getOffsetX() {
        return offsetX;
    }
    public int getOffsetY() {
        return offsetY;
    }
}