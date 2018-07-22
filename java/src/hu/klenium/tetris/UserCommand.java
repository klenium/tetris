package hu.klenium.tetris;

/**
 * All possible commands caused by a player. TetrisGame recieves only an UserCommand,
 * so that it does not depend on the input type (eg. key names, button types).
 */
public enum UserCommand {
    /**
     *
     */
    ROTATE,
    /**
     *
     */
    MOVE_LEFT,
    /**
     *
     */
    MOVE_DOWN,
    /**
     *
     */
    MOVE_RIGHT,
    /**
     *
     */
    DROP
}