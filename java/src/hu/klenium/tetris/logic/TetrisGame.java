package hu.klenium.tetris.logic;

import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.PeriodicTask;
import hu.klenium.tetris.util.Random;
import hu.klenium.tetris.view.GameFrame;

import static hu.klenium.tetris.logic.Command.*;

/**
 * Stores and controls a game's state, manages its components.
 */
public class TetrisGame {
    /**
     * Game's state: true when the tetromino is falling, false if game is over.
     */
    private boolean isRunning;
    /**
     * The game's board which holds the tetromino parts.
     */
    private final Board board;
    /**
     * The game's falling tetromino, the player can control it, but it'll
     * move down automatically too.
     */
    private Tetromino fallingTetromino;
    /**
     * A timer, in each period the tetromino will be moved down by one.
     */
    private final PeriodicTask gravity;
    /**
     * The frame of this game, manages its layout, user events.
     */
    private final GameFrame gameFrame;

    /**
     * Initalizes a new Tetris game.
     * @param size The board's size (rows/columns).
     * @param gameFrame This game's display area.
     */
    public TetrisGame(Dimension size, GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        board = new Board(size);
        gravity = new PeriodicTask(() -> handleCommand(FALL), 700);
    }

    /**
     * Starts the game: a new tetromino is created, and it starts falling.
     */
    public void start() {
        isRunning = true;
        generateNextTetromino();
        gameFrame.displayBoard(board);
        gameFrame.displayTetromino(fallingTetromino);
        gravity.start();
    }
    /**
     * Called when game is over, disables everything.
     */
    private void stop() {
        isRunning = false;
        gravity.stop();
        gameFrame.displayGameOver();
    }
    /**
     * Handles an user command when the player pressed an input button.
     * It controls the falling tetromino.
     * @param command The command that the player caused.
     */
    public void handleCommand(Command command) {
        if (!isRunning)
            return;
        boolean stateChanged = false;
        synchronized (this) {
            switch (command) {
                case ROTATE:
                    stateChanged = fallingTetromino.rotateRight();
                    break;
                case MOVE_LEFT:
                    stateChanged = fallingTetromino.moveLeft();
                    break;
                case MOVE_RIGHT:
                    stateChanged = fallingTetromino.moveRight();
                    break;
                case MOVE_DOWN:
                    gravity.reset();
                case FALL:
                    boolean moved = stateChanged = fallingTetromino.moveDown();
                    if (!moved)
                        tetrominoLanded();
                    break;
                case DROP:
                    boolean lastMovedDown;
                    do {
                        lastMovedDown = fallingTetromino.moveDown();
                        stateChanged |= lastMovedDown;
                    } while (lastMovedDown);
                    tetrominoLanded();
                    break;
            }
        }
        if (stateChanged && isRunning)
            gameFrame.displayTetromino(fallingTetromino);
    }

    /**
     * Called when the tetromino can't be moved down any more (ei. it fell down).
     * Adds the tetromino's parts to the board, removes full rows, generates next tetromino.
     */
    private void tetrominoLanded() {
        board.addTetromino(fallingTetromino);
        board.removeFullRows();
        gameFrame.displayBoard(board);
        boolean tetrominoAdded = generateNextTetromino();
        if (tetrominoAdded) {
            gravity.reset();
            gameFrame.displayTetromino(fallingTetromino);
        }
        else
            stop();
    }
    /**
     * Creates a new tetromino that will fall down next time.
     * @return True if it could be moved to its initial position, false otherwise.
     */
    private boolean generateNextTetromino() {
        int type = getNextTetrominoType();
        fallingTetromino = new Tetromino(type, board);
        return fallingTetromino.moveToInitialPos();
    }
    /**
     * Generates a number (type) for the next tetromino.
     * @return A randomly choosed tetromino type.
     */
    protected int getNextTetrominoType() {
        return Random.fromRange(0, 6);
    }
}