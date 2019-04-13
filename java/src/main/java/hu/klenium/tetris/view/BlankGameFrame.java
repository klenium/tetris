package hu.klenium.tetris.view;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import javafx.scene.input.KeyCode;

import java.util.Map;

public class BlankGameFrame implements GameFrame {
    public void registerEventListeners(TetrisGame game, Map<KeyCode, Command> keys) { }
    public void displayTetromino(Tetromino tetromino) {}
    public void displayBoard(Board board) {}
    public void displayGameOver() {}
}