package hu.klenium.tetris.logic.tetromino;

import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;

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
     * The type determines the shape of the tetromino. Each type has a
     * different shape, they has different views.
     * <br>
     * This is the index of {@link TetrominoDataFactory#rawData}.
     */
    private final int type;
    /**
     * Data of the tetromino in each rotation.
     * The data contains the tetromino's cells in that rotation,
     * and the bounding box's size.
     * <br>
     * The index is the rotation.
     * It's length can be less than 4 if two or more rotations have the same data.
     */
    private final TetrominoData[] parts;
    /**
     * Coordinates of this tetromino. It points to the top-left side
     * of the current bounding box (stored in {@link #parts}).
     */
    private Point position = new Point(0, 0);
    /**
     * Current rotation of the tetromino.
     * <br>
     * Its value can be bettween 0-3. It's used to index {@code parts}.
     */
    private int rotation = 0;

    /**
     * Initializes a new falling tetromino.
     * @param type Type of this tetromino.
     * @param board The game's board which this tetromino belongs to.
     */
    public Tetromino(int type, Board board) {
        this.type = type;
        this.board = board;
        parts = TetrominoDataFactory.getData(type);
    }

    /**
     * Gives access to the tetromino's data. It contaions each parts,
     * and their bounding box.
     * @return Parts in the current rotation.
     * @see #parts
     */
    public TetrominoData getCurrentData() {
        return parts[rotation];
    }
    /**
     * Tells the tetromino's position over the board's grid.
     * @return The top-left coordinate of the tetromino's bounding box.
     * @see #position
     */
    public Point getPosition() {
        return position;
    }
    /**
     * Tells this tetromino's type.
     * Used to display the falling tetromino, and create board cells.
     * @return The tetromino's type.
     * @see #type
     */
    public int getType() {
        return type;
    }

    /**
     * Tries to set the tetromino's initial position after it is created.
     * This position is the board's top-center point.
     * @return True if the tetromino could be moved to the specificed position,
     *         false otherwise (it also causes game over).
     */
    public boolean moveToInitialPos() {
        Dimension boardSize = board.getSize();
        float centerOfBoard = boardSize.width / 2.0f;
        Dimension tetrominoBoundingBox = parts[rotation].boundingBox;
        float halfTetrominoWidth = tetrominoBoundingBox.width / 2.0f;
        int centeredTetrominoPosX = (int) Math.ceil(centerOfBoard - halfTetrominoWidth);
        return tryPush(new Point(centeredTetrominoPosX, 0));
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
        if (canPushBy(new Point(0, 0)))
            canRotate = true;
        else {
            Dimension boundingBox = parts[rotation].boundingBox;
            for (int i = 1; i < boundingBox.width && !canRotate; ++i)
                canRotate = tryPush(new Point(-i, 0));
        }
        if (!canRotate)
            rotation = oldRotation;
        return canRotate;
    }
    /**
     * Tries to move the tetromino right by one.
     * @return True if the moving was successful, false otherwise.
     */
    public boolean moveRight() {
        return tryPush(new Point(1, 0));
    }
    /**
     * Tries to move the tetromino left by one.
     * @return True if the moving was successful, false otherwise.
     */
    public boolean moveLeft() {
        return tryPush(new Point(-1, 0));
    }
    /**
     * Tries to move the tetromino down by one.
     * @return True if the moving was successful, false otherwise.
     */
    public boolean moveDown() {
        return tryPush(new Point(0, 1));
    }

    /**
     * Check if moving is possible, and if it is, updates the tetromino's state.
     * @param delta Difference bettween the current and the target position.
     * @return True if the moving was successful, false otherwise.
     */
    private boolean tryPush(Point delta) {
        boolean canSlide = canPushBy(delta);
        if (canSlide)
            position = position.add(delta);
        return canSlide;
    }
    /**
     * Checks that the thetromino can be pushed away by the given values or not.
     * @param delta Difference bettween the current and the target position.
     * @return True if moving to position current+delta if possible, false otherwise.
     */
    private boolean canPushBy(Point delta) {
        return board.canAddTetromino(this, position.add(delta));
    }
}