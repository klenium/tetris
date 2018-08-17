import helpers.TestTetrisGame;
import helpers.TestUtil;
import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisGameSlowTest {
    private TestTetrisGame game;
    private static final int PERIOD = 500;
    private static final int OFFSET = PERIOD / 2;
    @BeforeEach public void setUp() {
        LinkedList<Integer> tetrominoTypes = new LinkedList<>();
        tetrominoTypes.add(3); // J
        tetrominoTypes.add(6); // T
        tetrominoTypes.add(1); // I
        tetrominoTypes.add(0); // O
        game = new TestTetrisGame(new Dimension(5, 6), tetrominoTypes, PERIOD);
    }
    @Test public void checkFalling() {
        game.start();
        Tetromino tetromino = game.getTetromino();
        assertEquals(tetromino.getPosition(), new Point(2, 0));
        TestUtil.runLater(OFFSET + PERIOD, () -> {
            assertEquals(tetromino.getPosition(), new Point(2, 1));
        });
        TestUtil.runLater(PERIOD, () -> {
            assertEquals(tetromino.getPosition(), new Point(2, 2));
        });
        TestUtil.runLater(PERIOD, () -> {
            assertEquals(tetromino.getPosition(), new Point(2, 3));
        });
    }
    @Test public void checlBoardSize() {
        assertEquals(game.getBoard().getSize().width, 5);
        assertEquals(game.getBoard().getSize().height, 6);
    }
    @Test public void unStartedState() {
        assertFalse(game.getState());
        assertNull(game.getTetromino());
        game.handleCommand(Command.MOVE_DOWN);
        assertNull(game.getTetromino());
    }
    @Test public void runningState() {
        game.start();
        assertTrue(game.getState());
        assertNotNull(game.getTetromino());
    }
    @Test public void stoppedState() {
        game.start();
        TestUtil.controlTetromino(game, " ");
        TestUtil.controlTetromino(game, " ");
        Tetromino tetromino1 = game.getTetromino();
        assertFalse(game.getState());
        TestUtil.controlTetromino(game, " ");
        Tetromino tetromino2 = game.getTetromino();
        assertSame(tetromino1, tetromino2);
        assertEquals(tetromino1.getPosition(), tetromino2.getPosition());
    }
    @Test public void controlTetromino() {
        game.start();
        Tetromino tetromino = game.getTetromino();
        Point position1 = tetromino.getPosition();
        game.handleCommand(Command.MOVE_LEFT);
        Point position2 = tetromino.getPosition();
        assertNotEquals(position1, position2);
    }
    @Test public void tetrominoLanded() {
        game.start();
        Tetromino tetromino1 = game.getTetromino();
        game.handleCommand(Command.DROP);
        Tetromino tetromino2 = game.getTetromino();
        assertNotSame(tetromino1, tetromino2);
        assertEquals(tetromino2.getType(), 6);
        TestUtil.checkBoardState(game.getBoard(), new String[] {
            ".....",
            ".....",
            ".....",
            "...#.",
            "...#.",
            "..##."
        });
    }
    @Test public void removeFullRows() {
        game.start();
        TestUtil.controlTetromino(game, "DD ");
        TestUtil.controlTetromino(game, "WWA ");
        TestUtil.checkBoardState(game.getBoard(), new String[] {
            ".....",
            ".....",
            ".....",
            ".....",
            "....#",
            ".#..#"
        });
    }
    @Test public void initialPosition() {
        game.start();
        Tetromino tetromino = game.getTetromino();
        assertEquals(tetromino.getPosition(), new Point(2, 0));
        TestUtil.controlTetromino(game, "D ");
        tetromino = game.getTetromino();
        assertEquals(tetromino.getPosition(), new Point(1, 0));
        TestUtil.controlTetromino(game, " ");
        tetromino = game.getTetromino();
        assertEquals(tetromino.getPosition(), new Point(2, 0));
    }
}