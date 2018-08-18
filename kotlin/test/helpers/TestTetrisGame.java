package helpers;

import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;

import java.util.LinkedList;

public class TestTetrisGame extends TetrisGame {
    private LinkedList<Integer> tetrominoTypes;
    public TestTetrisGame(Dimension size, LinkedList<Integer> tetrominoTypes) {
        super(size);
        this.tetrominoTypes = tetrominoTypes;
    }
    @Override protected int getNextTetrominoType() {
        Integer type = tetrominoTypes.removeFirst();
        if (type == null)
            throw new RuntimeException("No more tetromino types has been set.");
        return type;
    }
    public Tetromino getTetromino() {
        return getFallingTetromino();
    }
    public Board getGameBoard() {
        return super.getBoard();
    }
    public boolean getState() {
        return isRunning();
    }
}