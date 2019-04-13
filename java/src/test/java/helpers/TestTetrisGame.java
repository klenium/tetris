package helpers;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.view.BlankGameFrame;
import hu.klenium.tetris.view.GameFrame;

import java.util.LinkedList;

public class TestTetrisGame extends TetrisGame {
    private LinkedList<Integer> tetrominoTypes;
    private Command lastCommand = null;
    public TestTetrisGame(Dimension size, GameFrame frame, LinkedList<Integer> tetrominoTypes, int fallingSpeed) {
        super(size, frame, fallingSpeed);
        this.tetrominoTypes = tetrominoTypes;
    }
    public static TestTetrisGame createDefault(Dimension gridSize, GameFrame frame, int fallingSpeed) {
        LinkedList<Integer> tetrominoTypes = new LinkedList<>();
        tetrominoTypes.add(3); // J
        tetrominoTypes.add(6); // T
        tetrominoTypes.add(1); // I
        tetrominoTypes.add(0); // O
        return new TestTetrisGame(gridSize, frame, tetrominoTypes, fallingSpeed);
    }
    @Override public void handleCommand(Command command) {
        super.handleCommand(command);
        lastCommand = command;
    }
    /*
    Using random numbers in testing is not a good idea, it adds additional xomplexity
    to the test, which may be wrong too. Hence, random tetromino generation is disabled
    during testing.
     */
    @Override protected int getNextTetrominoType() {
        return tetrominoTypes.isEmpty() ? 0 : tetrominoTypes.removeFirst();
    }
    public Command getLastCommand() {
        Command result = lastCommand;
        lastCommand = null;
        return result;
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