import helpers.TestUtil;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoTest {
    private Board board = null;
    private Tetromino tetromino = null;
    @BeforeEach public void setUp() {
        board = new Board(new Dimension(7, 8));
    }
    @Test public void checkTetrominoTypeOGrid() {
        tetromino = new Tetromino(0, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "##",
            "##"
        });
    }
    @Test public void checkTetrominoTypeIGrid() {
        tetromino = new Tetromino(1, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "#",
            "#",
            "#",
            "#"
        });
    }
    @Test public void checkTetrominoTypeLGrid() {
        tetromino = new Tetromino(2, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "#.",
            "#.",
            "##"
        });
    }
    @Test public void checkTetrominoTypeJGrid() {
        tetromino = new Tetromino(3, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
            ".#",
            ".#",
            "##"
        });
    }
    @Test public void checkTetrominoTypeSGrid() {
        tetromino = new Tetromino(4, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
                ".##",
                "##."
        });
    }
    @Test public void checkTetrominoTypeZGrid() {
        tetromino = new Tetromino(5, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "##.",
            ".##"
        });
    }
    @Test public void checkTetrominoTypeTGrid() {
        tetromino = new Tetromino(6, board);
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "###",
            ".#."
        });
    }
    @Test public void moveToInitialPosition() {
        createTetrominoTypeT();
        Point position;
        position = tetromino.getPosition();
        assertEquals(position, new Point(0, 0));
        tetromino.moveToInitialPos();
        position = tetromino.getPosition();
        assertEquals(position, new Point(2, 0));
    }
    @Test public void moveToInitialPositionRounding() {
        createTetrominoTypeI();
        tetromino.rotateRight();
        tetromino.moveToInitialPos();
        Point position = tetromino.getPosition();
        assertEquals(position, new Point(2, 0));
    }
    @Test public void rotateTypeT() {
        createTetrominoTypeT();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "###",
            ".#."
        });
        tetromino.rotateRight();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "#.",
            "##",
            "#."
        });
        tetromino.rotateRight();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            ".#.",
            "###"
        });
        tetromino.rotateRight();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            ".#",
            "##",
            ".#"
        });
        tetromino.rotateRight();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "###",
            ".#."
        });
    }
    @Test public void rotateTypeI() {
        createTetrominoTypeI();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "#",
            "#",
            "#",
            "#"
        });
        tetromino.rotateRight();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "####"
        });
        tetromino.rotateRight();
        TestUtil.checkTetrominoState(tetromino, new String[] {
            "#",
            "#",
            "#",
            "#"
        });
    }
    @Test public void cantRotate() {
        createTetrominoTypeI();
        TestUtil.controlTetromino(tetromino, "DDSSSS");
        board.addTetromino(tetromino);
        createTetrominoTypeT();
        TestUtil.controlTetromino(tetromino, "WSSS");
        assertFalse(TestUtil.controlTetromino(tetromino, "W"));
    }
    @Test public void moveLeftWhenRotatingAtRightSide() {
        createTetrominoTypeI();
        TestUtil.controlTetromino(tetromino, "DDDDD");
        assertEquals(tetromino.getPosition(), new Point(5, 0));
        TestUtil.controlTetromino(tetromino, "W");
        assertEquals(tetromino.getPosition(), new Point(3, 0));
    }
    @Test public void dontMoveLeftWhenRotatingAtRightSideIsntPossible() {
        createTetrominoTypeI();
        TestUtil.controlTetromino(tetromino, "DDDSSSS");
        board.addTetromino(tetromino);
        createTetrominoTypeI();
        TestUtil.controlTetromino(tetromino, "DDDDDDSSSS");
        assertFalse(TestUtil.controlTetromino(tetromino, "W"));
    }
    @Test public void moveLeft() {
        createTetrominoTypeT();
        tetromino.moveToInitialPos();
        TestUtil.controlTetromino(tetromino, "A");
        Point position = tetromino.getPosition();
        assertEquals(position, new Point(1, 0));
    }
    @Test public void moveDown() {
        createTetrominoTypeT();
        tetromino.moveToInitialPos();
        TestUtil.controlTetromino(tetromino, "S");
        Point position = tetromino.getPosition();
        assertEquals(position, new Point(2, 1));
    }
    @Test public void moveRight() {
        createTetrominoTypeT();
        tetromino.moveToInitialPos();
        TestUtil.controlTetromino(tetromino, "D");
        Point position = tetromino.getPosition();
        assertEquals(position, new Point(3, 0));
    }

    private void createTetrominoTypeT() {
        tetromino = new Tetromino(6, board);
    }
    private void createTetrominoTypeI() {
        tetromino = new Tetromino(1, board);
    }
}