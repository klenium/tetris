package hu.klenium.tetris;

import hu.klenium.tetris.util.PeriodicTask;
import hu.klenium.tetris.view.BoardView;
import hu.klenium.tetris.view.TetrominoView;
import hu.klenium.tetris.window.MainWindow;

import java.util.Random;

public class TetrisGame {
    private final static Random random = new Random();
    private final int blockSize = 30;
    private final int columns = 11;
    private final int rows = 16;

    private boolean isRunning;
    private static TetrisGame instance;
    private final Board board;
    private Tetromino fallingTetromino;
    private final PeriodicTask gravity;
    private final MainWindow window;

    private TetrisGame(MainWindow window) {
        this.window = window;
        BoardView view = new BoardView(window, blockSize);
        board = new Board(rows, columns, view);
        gravity = new PeriodicTask(() -> {
            boolean moved = fallingTetromino.moveDown();
            if (!moved)
                tetrominoCantMoveFurther();
        }, 700);
    }
    public static TetrisGame createNew(MainWindow window) {
        instance = new TetrisGame(window);
        return instance;
    }
    public static TetrisGame getInstance() {
        return instance;
    }

    public void start() {
        isRunning = true;
        generateNextTetromino();
        gravity.start();
    }
    private void stop() {
        isRunning = false;
        fallingTetromino.dispose();
        fallingTetromino = null;
        gravity.stop();
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

    public boolean canMoveTetrominoTo(Tetromino tetromino, int x, int y) {
        return board.canAddTetromino(tetromino, x, y);
    }

    private void tetrominoCantMoveFurther() {
        board.addTetromino(fallingTetromino);
        board.removeFullRows();
        generateNextTetromino();
    }
    private void generateNextTetromino() {
        if (fallingTetromino != null)
            fallingTetromino.dispose();
        int type = random.nextInt(7);
        TetrominoView view = new TetrominoView(window, blockSize);
        Tetromino next = Tetromino.create(type, view, columns);
        fallingTetromino = next;
        if (next != null)
            gravity.reset();
        else
            stop();
    }
}