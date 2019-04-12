import helpers.TestUtil;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    @BeforeEach public void setUp() {
        board = new Board(new Dimension(5, 4));
    }
    @Test public void newBoardIsEmpty() {
        TestUtil.checkBoardState(board, new String[]{
            ".....",
            ".....",
            ".....",
            "....."
        });
    }
    @Test public void addTetrominoes() {
        addTestData();
        TestUtil.checkBoardState(board, new String[]{
            ".....",
            "....#",
            ".#.##",
            ".####"
        });
    }
    @Test public void movingTetrominoInsideEmptyBoard() {
        Tetromino tetromino = new Tetromino(0, board);
        assertTrue(TestUtil.controlTetromino(tetromino, "DADDDSAADSDAD"));
    }
    @Test public void movingTetrominoOutsideBoardBox() {
        Tetromino tetromino = new Tetromino(0, board);
        assertFalse(board.canAddTetromino(tetromino, new Point(0, -1)));
        assertFalse(TestUtil.controlTetromino(tetromino, "A"));
        assertFalse(TestUtil.controlTetromino(tetromino, "DDDD"));
        assertFalse(TestUtil.controlTetromino(tetromino, "SSS"));
    }
    @Test public void invalidTetrominoMoveInsideUsedBoard() {
        addTestData();
        Tetromino tetromino = new Tetromino(0, board);
        assertFalse(TestUtil.controlTetromino(tetromino, "S"));
        assertFalse(TestUtil.controlTetromino(tetromino, "DDD"));
    }
    @Test public void removeOneFullRow() {
        addTestData();
        board.removeFullRows();
        TestUtil.checkBoardState(board, new String[]{
            ".....",
            "....#",
            ".#.##",
            ".####"
        });
        Tetromino tetromino = new Tetromino(1, board);
        board.addTetromino(tetromino);
        TestUtil.checkBoardState(board, new String[]{
            "#....",
            "#...#",
            "##.##",
            "#####"
        });
        board.removeFullRows();
        TestUtil.checkBoardState(board, new String[]{
            ".....",
            "#....",
            "#...#",
            "##.##"
        });
    }
    @Test public void removeMultipleFullRows() {
        Tetromino tetromino;
        addTestData();
        board.removeFullRows();
        tetromino = new Tetromino(6, board);
        TestUtil.controlTetromino(tetromino, "DS");
        board.addTetromino(tetromino);
        tetromino = new Tetromino(1, board);
        board.addTetromino(tetromino);
        board.removeFullRows();
        TestUtil.checkBoardState(board, new String[]{
            ".....",
            ".....",
            ".....",
            "#...."
        });
    }

    public void addTestData() {
            /* .....
               ....#
               .#.##
               .#### */
        Tetromino tetromino;
        tetromino = new Tetromino(3, board);
        TestUtil.controlTetromino(tetromino, "WWWDSS");
        board.addTetromino(tetromino);
        tetromino = new Tetromino(6, board);
        TestUtil.controlTetromino(tetromino, "WWDDWDS");
        board.addTetromino(tetromino);
    }
}