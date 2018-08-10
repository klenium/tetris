package hu.klenium.tetris.logic;

/**
 * Command specify an event's type, which is caused by the game or its player to
 * control the falling tetromino.
 * <br>
 * TetrisGame recieves only a command, so it does not depend on
 * the input type (eg. key names, button types).
 */
public enum Command {
    /**
     * Rotate the falling tetromino by 90 degrees to right.
     */
    ROTATE,
    /**
     * Move the falling tetromino left by one.
     */
    MOVE_LEFT,
    /**
     * Move the falling tetromino down by one.
     */
    MOVE_DOWN,
    /**
     * Move the falling tetromino right by one.
     */
    MOVE_RIGHT,
    /**
     * Move down the falling tetromino as much as possible in one step.
     */
    DROP,
    /**
     * Similar to {@link #MOVE_DOWN}, but this event is caused by the game
     * periodically to simulate the falling effect.
     */
    FALL
}