package helpers;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.view.GameFrame;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.Map;

public class TestTetrisGame extends TetrisGame {
    private LinkedList<Integer> tetrominoTypes;
    public TestTetrisGame(Dimension size, LinkedList<Integer> tetrominoTypes, int fallingSpeed) {
        super(size, new GameFrame() {
            // Testing doesn't display anything.
            public void registerEventListeners(TetrisGame game, Map<KeyCode, Command> keys) { }
            public void displayTetromino(Tetromino tetromino) {}
            public void displayBoard(Board board) {}
            public void displayGameOver() {}
        }, fallingSpeed);
        this.tetrominoTypes = tetrominoTypes;
    }
    /*
    Using random numbers in testing is not a good idea, it adds additional xomplexity
    to the test, which may be wrong too. Hence, random tetromino generation is disabled
    during testing.
     */
    @Override protected int getNextTetrominoType() {
        Integer type = tetrominoTypes.removeFirst();
        if (type == null)
            throw new RuntimeException("No more tetromino types has been set.");
        return type;
    }
    public Tetromino getTetromino() {
        return fallingTetromino;
    }
    public Board getBoard() {
        return board;
    }
    public boolean getState() {
        return isRunning;
    }
}