package hu.klenium.tetris;

import hu.klenium.tetris.view.TetrominoView;

/**
 * A shape in Tetris game, which can be controlled by the player.
 * <br>
 * A tetromino is a geometric shape composed of four squares, connected orthogonally.
 * The tetromino is part of one board. It can be moved left, right, or down,
 * and can be rotated to right by the player. These operations may fail if the board
 * is not empty at some point. Tetrominoes can't overlap previously fallen tetromino
 * parts located on the board. When the tetromino can't move down any more, it's added
 * to the board's table, and a new Tetromino is created.
 */
public class Tetromino {
    /**
     * The board which this tetromino belongs to.
     */
    private final Board board;
    /**
     * Data of the tetromino in each rotation.
     * The data contains the tetromino's cells in that rotation,
     * and the bounding box's size.
     * The index is the rotation.
     * It's length can be less than 4 if two or more rotations have the same data.
     */
    private final TetrominoData[] parts;
    /**
     * The view which displays this tetromino.
     */
    private final TetrominoView view;
    /**
     * X position of the tetromino.
     * It specify the top point of its current data in the board.
     */
    private int currentX = 0;
    /**
     * Y position of the tetromino.
     * It specify the left point of its current data in the board.
     */
    private int currentY = 0;
    /**
     * Current rotation of the tetromino. Its value can be bettween 0-3.
     * It's used to index {@code parts}.
     */
    private int rotation = 0;

    /**
     * Initializes a new falling tetromino.
     * @param type Type of this tetromino.
     * @param view The view used by this tetromino.
     * @param board The game's board which this tetromino belongs to.
     */
    public Tetromino(int type, TetrominoView view, Board board) {
        this.view = view;
        this.board = board;
        parts = TetrominoDataFactory.getData(type);
    }
    /**
     * Called when the tetromino is destroyed.
     * Notifies its view about the event.
     */
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

    /**
     * Tries to set the tetromino's initial position after it is created.
     * This position is the board's top-center point.
     * @return True if the tetromino could be moved to the specificed position,
     *         false otherwise (it also causes game over).
     */
    public boolean moveToInitialPos() {
        float centerOfBoard = board.getWidth() / 2.0f;
        float halfTetrominoWidth = parts[rotation].getWidth() / 2.0f;
        int centeredTetrominoPosX = (int) Math.ceil(centerOfBoard - halfTetrominoWidth);
        return tryPush(centeredTetrominoPosX, 0);
    }
    /**
     * Tries to rotate the tetromino by 90 degrees to right.
     * If it isn't possible, it'll try to move the tetromino away horizontally, so that if
     * the tetromino is near the board's edge, the player still can perform the rotation easily.
     * @return True if the rotating was successful, false otherwise.
     */
    public boolean rotateRight() {
        int nextRotation = (rotation + 1) % parts.length;
        boolean canRotate = false;
        int oldRotation = rotation;
        rotation = nextRotation;
        if (canPushBy(0, 0))
            canRotate = true;
        else {
            int width = parts[rotation].getWidth();
            for (int i = 1; i < width && !canRotate; ++i) {
                if (canPushBy(-i, 0)) {
                    currentX -= i;
                    canRotate = true;
                }
            }
        }
        if (!canRotate)
            rotation = oldRotation;
        else
            updateView();
        return canRotate;
    }
    /**
     * Tries to move the tetromino right by one.
     * @return True if the moving was successful, false otherwise.
     */
    public boolean moveRight() {
        return tryPush(1, 0);
    }
    /**
     *Tries to move the tetromino left by one.
     * @return True if the moving was successful, false otherwise.
     */
    public boolean moveLeft() {
        return tryPush(-1, 0);
    }
    /**
     * Tries to move the tetromino down by one.
     * @return True if the moving was successful, false otherwise.
     */
    public boolean moveDown() {
        return tryPush(0, 1);
    }
    /**
     * In one step, moves down the tetromino as much as possible.
     */
    public void drop() {
        boolean movedDown;
        do {
            movedDown = moveDown();
        } while (movedDown);
    }

    /**
     * Called when the its state is updated and need to be displayed again.
     * Passes the needed data to the view, which will draw it.
     */
    private void updateView() {
        view.update(parts[rotation].getParts(), currentX, currentY);
    }
    /**
     * Check if moving is possible, and if it is, updates the tetromino's state, view.
     * @param x Difference from current X coordinate.
     * @param y Difference from current Y coordinate.
     * @return True if the moving was successful, false otherwise.
     */
    private boolean tryPush(int x, int y) {
        boolean canSlide = canPushBy(x, y);
        if (canSlide) {
            currentX += x;
            currentY += y;
            updateView();
        }
        return canSlide;
    }
    /**
     * Checks whatever the thetromino can be pushed away by the given values.
     * @param deltaX The value how far it is wanted to be pshed horizontally.
     * @param deltaY The value how far it is wanted to be pshed vertically.
     * @return True if moving to position current+delta if possible, false otherwise.
     */
    private boolean canPushBy(int deltaX, int deltaY) {
        return board.canAddTetromino(this, currentX + deltaX, currentY + deltaY);
    }
}