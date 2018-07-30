package hu.klenium.tetris;

import hu.klenium.tetris.testhelper.TestBoardView;
import hu.klenium.tetris.testhelper.TestTetrominoView;
import hu.klenium.tetris.view.BoardCell;
import hu.klenium.tetris.view.GraphicTetrominoView;
import hu.klenium.tetris.view.TetrominoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private TestBoardView view;
    @BeforeEach void setUp() {
        view = new TestBoardView();
        board = new Board(6, 6, view);
    }
    private Tetromino createTetromino(int type) {
        TetrominoView tetrominoView = new TestTetrominoView();
        return new Tetromino(type, tetrominoView, board);
    }
    private void executeCommands(Tetromino tetromino, String commands) {
        char[] data = commands.toCharArray();
        for (char ch : data) {
            switch (ch) {
                case 'W': tetromino.rotateRight(); break;
                case 'A': tetromino.moveRight(); break;
                case 'S': tetromino.moveDown(); break;
                case 'D': tetromino.moveLeft(); break;
                case 'F': tetromino.drop(); break;
            }
        }
    }
    @Test public void emptyBoardState() {
        BoardCell[][] data = view.getData();
        for (BoardCell[] row : data) {
            for (BoardCell cell : row)
                assertTrue(cell.isEmpty());
        }
    }
    @Test public void addTetromino() {
        Tetromino tetromino = createTetromino(6);
        executeCommands(tetromino, "");
        board.addTetromino(tetromino);
    }
    @Test public void canAddTetromino() {
        Tetromino tetromino = createTetromino(6);

    }
}