package hu.klenium.tetris;

/**
 * A command is an event type, caused by the game's player to control
 * the falling tetromino.
 * <br>
 * TetrisGame recieves only an UserCommand, so that it does not depend on
 * the input type (eg. key names, button types).
 */
public enum UserCommand {
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
    DROP
}