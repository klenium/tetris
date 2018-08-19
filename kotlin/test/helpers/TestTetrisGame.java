package helpers;

import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import kotlin.UninitializedPropertyAccessException;

import java.util.LinkedList;

public class TestTetrisGame extends TetrisGame {
    private LinkedList<Integer> tetrominoTypes;
    public TestTetrisGame(Dimension size, LinkedList<Integer> tetrominoTypes, int fallingSpeed) {
        super(size, fallingSpeed);
        this.tetrominoTypes = tetrominoTypes;
    }
    @Override protected int getNextTetrominoType() {
        return tetrominoTypes.removeFirst();
    }
    public Tetromino getTetromino() {
        try {
            return getFallingTetromino();
        } catch (UninitializedPropertyAccessException e) {
            return null;
        }
    }
    public Board getGameBoard() {
        return super.getBoard();
    }
    public boolean getState() {
        return isRunning();
    }
}