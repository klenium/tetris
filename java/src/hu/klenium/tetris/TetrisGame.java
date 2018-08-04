package hu.klenium.tetris;

import hu.klenium.tetris.util.PeriodicTask;
import hu.klenium.tetris.view.BoardView;
import hu.klenium.tetris.view.GraphicBoardView;
import hu.klenium.tetris.view.GraphicTetrominoView;
import hu.klenium.tetris.view.window.GameFrame;
import hu.klenium.tetris.view.window.MainApplication;

import java.util.Random;

/**
 * Stores and controls a game's state, manages its components.
 */
public class TetrisGame {
    private final static Random random = new Random();
    /**
     * Size of board/tetromino cells (ie. how big are the squares).
     */
    private final int blockSize = 30;

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
     * Creates a new frame for the game, binds event listeners.
     * Creates the board and its view.
     * Prepares the grafity timer.
     * @param columns The width of this game's board.
     * @param rows The height of this game's board.
     */
    public TetrisGame(int columns, int rows) {
        gameFrame = MainApplication.createFrame();
        gameFrame.setSize(columns * blockSize, rows * blockSize);
        gameFrame.registerEventListeners(this);
        BoardView view = new GraphicBoardView(gameFrame, blockSize);
        board = new Board(rows, columns, view);
        gravity = new PeriodicTask(() -> {
            synchronized (this) {
                boolean moved = fallingTetromino.moveDown();
                if (!moved)
                    tetrominoCantMoveFurther();
            }
        }, 700);
    }

    /**
     * Starts the game: a new tetromino is created, and it starts falling.
     */
    public void start() {
        isRunning = true;
        generateNextTetromino();
        gravity.start();
    }
    /**
     * Called when game is over, disables everything.
     */
    private void stop() {
        isRunning = false;
        gravity.stop();
        fallingTetromino.dispose();
    }
    /**
     * Handles an user command when the player pressed an input button.
     * It controls the falling tetromino.
     * @param command The command that the player caused.
     */
    public void handleCommand(UserCommand command) {
        if (!isRunning)
            return;
        switch (command) {
            case ROTATE:
                fallingTetromino.rotateRight();
                break;
            case MOVE_LEFT:
                fallingTetromino.moveLeft();
                break;
            case MOVE_DOWN:
                if (fallingTetromino.moveDown())
                    gravity.reset();
                else
                    tetrominoCantMoveFurther();
                break;
            case MOVE_RIGHT:
                fallingTetromino.moveRight();
                break;
            case DROP:
                fallingTetromino.drop();
                tetrominoCantMoveFurther();
                break;
        }
    }

    /**
     * Called when the tetromino can't be moved down any more (ei. it fell down).
     * Adds the tetromino's parts to the board, removes full rows, generates next tetromino.
     */
    private void tetrominoCantMoveFurther() {
        board.addTetromino(fallingTetromino);
        board.removeFullRows();
        fallingTetromino.dispose();
        boolean tetrominoAdded = generateNextTetromino();
        if (tetrominoAdded)
            gravity.reset();
        else
            stop();
    }
    /**
     * Creates a new falling tetromino.
     * @return True if it could be moved to its initial position, false otherwise.
     */
    private boolean generateNextTetromino() {
        int type = random.nextInt(7);
        GraphicTetrominoView view = new GraphicTetrominoView(gameFrame, blockSize);
        fallingTetromino = new Tetromino(type, view, board);
        return fallingTetromino.moveToInitialPos();
    }
}