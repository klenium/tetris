package unit;

import helpers.TestTetrisGame;
import helpers.TestUtil;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import hu.klenium.tetris.view.BlankGameFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisGameSlowTest {
    private TestTetrisGame game;
    private static final int PERIOD = 500;
    private static final int OFFSET = PERIOD / 2;
    @BeforeEach public void setUp() {
        game = TestTetrisGame.createDefault(new Dimension(5, 6), new BlankGameFrame(), PERIOD);
    }
    @Test public void checkFalling() {
        game.start();
        Tetromino tetromino = game.getTetromino();
        assertEquals(tetromino.getPosition(), new Point(2, 0));
        TestUtil.runLater(OFFSET + PERIOD, () ->
            assertEquals(tetromino.getPosition(), new Point(2, 1))
        );
        TestUtil.runLater(PERIOD, () ->
            assertEquals(tetromino.getPosition(), new Point(2, 2))
        );
        TestUtil.runLater(PERIOD, () ->
            assertEquals(tetromino.getPosition(), new Point(2, 3))
        );
    }
    @Test public void checlBoardSize() {
        assertEquals(5, game.getBoard().getSize().width);
        assertEquals(6, game.getBoard().getSize().height);
    }
    @Test public void unStartedState() {
        assertFalse(game.getState());
        assertNull(game.getTetromino());
        TestUtil.controlTetromino(game, "S");
        assertNull(game.getTetromino());
    }
    @Test public void cantStartGame() {
        LinkedList<Integer> tetrominoTypes = new LinkedList<>();
        tetrominoTypes.add(1); // I
        game = new TestTetrisGame(new Dimension(5, 3), new BlankGameFrame(), tetrominoTypes, PERIOD);
        game.start();
        assertFalse(game.getState());
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
        List<Point> positions = new LinkedList<>();
        positions.add(tetromino.getPosition());
        TestUtil.controlTetromino(game, "A");
        positions.add(tetromino.getPosition());
        TestUtil.controlTetromino(game, "S");
        positions.add(tetromino.getPosition());
        TestUtil.controlTetromino(game, "D");
        positions.add(tetromino.getPosition());
        Set<Point> uniques = new HashSet<>(positions);
        assertSame(positions.size(), uniques.size());
    }
    @Test public void tetrominoLanded() {
        game.start();
        Tetromino tetromino1 = game.getTetromino();
        TestUtil.controlTetromino(game, " ");
        Tetromino tetromino2 = game.getTetromino();
        assertNotSame(tetromino1, tetromino2);
        assertEquals(6, tetromino2.getType());
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