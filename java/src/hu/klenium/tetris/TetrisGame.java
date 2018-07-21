package hu.klenium.tetris;

import hu.klenium.tetris.util.PeriodicTask;
import hu.klenium.tetris.view.BoardView;
import hu.klenium.tetris.view.TetrominoView;
import hu.klenium.tetris.window.GameFrame;
import hu.klenium.tetris.window.MainApplication;

import java.util.Random;

public class TetrisGame {
    private final static Random random = new Random();
    private final int blockSize = 30;
    private final int columns = 11;
    private final int rows = 16;

    private boolean isRunning;
    private final Board board;
    private Tetromino fallingTetromino;
    private final PeriodicTask gravity;
    private final GameFrame gameFrame;

    public TetrisGame() {
        gameFrame = MainApplication.createFrame();
        gameFrame.setSize(columns * blockSize, rows * blockSize);
        gameFrame.registerEventListeners(this);
        BoardView view = new BoardView(gameFrame, blockSize);
        board = new Board(rows, columns, view);
        gravity = new PeriodicTask(() -> {
            synchronized (this) {
                boolean moved = fallingTetromino.moveDown();
                if (!moved)
                    tetrominoCantMoveFurther();
            }
        }, 700);
    }

    public void start() {
        isRunning = true;
        generateNextTetromino();
        gravity.start();
    }
    private void stop() {
        isRunning = false;
        gravity.stop();
        fallingTetromino.dispose();
    }
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
    private boolean generateNextTetromino() {
        int type = random.nextInt(7);
        TetrominoView view = new TetrominoView(gameFrame, blockSize);
        fallingTetromino = new Tetromino(type, view, board);
        return fallingTetromino.moveToInitialPos();
    }
}