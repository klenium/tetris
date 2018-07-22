package hu.klenium.tetris;

import hu.klenium.tetris.view.TetrominoView;

public class Tetromino {
    private final Board board;
    private final TetrominoData[] parts;
    private final TetrominoView view;
    private int currentX = 0;
    private int currentY = 0;
    private int rotation;
    private int width;
    private int height;

    public Tetromino(int type, TetrominoView view, Board board) {
        this.view = view;
        this.board = board;
        parts = TetrominoDataFactory.getData(type);
        setRotation(0);
    }
    public void dispose() {
        view.clear();
    }

    public TetrominoData getData() {
        return parts[rotation];
    }
    public int getPosX() {
        return currentX;
    }
    public int getPosY() {
        return currentY;
    }

    public boolean moveToInitialPos() {
        float centerOfBoard = board.getWidth() / 2;
        float halfTetrominoWidth = width / 2;
        int centeredTetrominoPosX = (int) Math.ceil(centerOfBoard - halfTetrominoWidth);
        return tryMove(centeredTetrominoPosX, 0);
    }
    public boolean rotateRight() {
        int nextRotation = (rotation + 1) % 4;
        boolean canRotate = false;
        int oldRotation = rotation;
        setRotation(nextRotation);
        if (canMoveTo(0, 0))
            canRotate = true;
        else {
            for (int i = 1; i < width && !canRotate; ++i) {
                if (canMoveTo(-i, 0)) {
                    currentX -= i;
                    canRotate = true;
                }
            }
        }
        if (!canRotate)
            setRotation(oldRotation);
        else
            updateView();
        return canRotate;
    }
    public boolean moveRight() {
        return tryMove(1, 0);
    }
    public boolean moveLeft() {
        return tryMove(-1, 0);
    }
    public boolean moveDown() {
        return tryMove(0, 1);
    }
    public void drop() {
        boolean movedDown;
        do {
            movedDown = moveDown();
        } while (movedDown);
    }

    private void setRotation(int rotation) {
        this.rotation = rotation % parts.length;
        height = parts[this.rotation].getHeight();
        width = parts[this.rotation].getWidth();
    }
    private void updateView() {
        view.update(parts[rotation].getParts(), currentX, currentY);
    }
    private boolean tryMove(int x, int y) {
        boolean canSlide = canMoveTo(x, y);
        if (canSlide) {
            currentX += x;
            currentY += y;
            updateView();
        }
        return canSlide;
    }
    private boolean canMoveTo(int deltaX, int deltaY) {
        return board.canAddTetromino(this, currentX + deltaX, currentY + deltaY);
    }
}