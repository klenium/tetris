package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;

public class TetrominoPart {
    private final SquareView squareView;
    private final int offsetX;
    private final int offsetY;
    public TetrominoPart(SquareView square, int x, int y) {
        squareView = square;
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