package hu.klenium.tetris.logic;

import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.PeriodicTask;
import hu.klenium.tetris.util.Random;
import hu.klenium.tetris.view.GameFrame;

/**
 * Stores and controls a game's state, manages its components.
 */
public class TetrisGame {
    /**
     * Game's state: true when the tetromino is falling, false if game is over.
     */
    protected boolean isRunning;
    /**
     * The game's board which holds the tetromino parts.
     */
    protected final Board board;
    /**
     * The game's falling tetromino, the player can control it, but it'll
     * move down automatically too.
     */
    protected Tetromino fallingTetromino;
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
     * @param fallingSpeed After this much time, the tetromino will automatically move down.
     */
    public TetrisGame(Dimension size, GameFrame gameFrame, int fallingSpeed) {
        this.gameFrame = gameFrame;
        board = new Board(size);
        gravity = new PeriodicTask(() -> handleCommand(Command.FALL), fallingSpeed);
    }

    /**
     * Starts the game: a new tetromino is created, and it starts falling.
     */
    public void start() {
        isRunning = generateNextTetromino();
        if (isRunning) {
            gameFrame.displayBoard(board);
            gameFrame.displayTetromino(fallingTetromino);
            gravity.start();
        }
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
                case ROTATE: stateChanged = rotateTetromino(); break;
                case MOVE_LEFT: stateChanged = moveTetrominoLeft(); break;
                case MOVE_RIGHT: stateChanged = moveTetrominoRight(); break;
                case FALL: stateChanged = moveTetrominoDown(); break;
                case MOVE_DOWN:
                    gravity.reset();
                    stateChanged = moveTetrominoDown();
                    break;
                case DROP:
                    boolean lastMovedDown;
                    do {
                        lastMovedDown = moveTetrominoDown();
                        stateChanged |= lastMovedDown;
                    } while (lastMovedDown);
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

    private boolean moveTetrominoLeft() {
        return fallingTetromino.moveLeft();
    }
    private boolean moveTetrominoDown() {
        boolean moved = fallingTetromino.moveDown();
        if (!moved)
            tetrominoLanded();
        return moved;
    }
    private boolean moveTetrominoRight() {
        return fallingTetromino.moveRight();
    }
    private boolean rotateTetromino() {
        return fallingTetromino.rotateRight();
    }
}