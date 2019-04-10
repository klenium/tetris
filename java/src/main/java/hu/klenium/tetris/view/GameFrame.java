package hu.klenium.tetris.view;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import javafx.scene.input.KeyCode;

import java.util.Map;

/**
 * View interface of a Tetris game. Used to display the game's state, and
 * handle user inputs.
 */
public interface GameFrame {
    /**
     * Registers handlers for user inputs. When one of the specificed keys is pressed,
     * sends the corresponding command to {@code game}.
     * @param game The game which will recieve a command on user input.
     * @param keys List of keys which are used to control the falling tetromino.
     */
    void registerEventListeners(TetrisGame game, Map<KeyCode, Command> keys);
    /**
     * Called when the falling tetromino's state is updated and so need to be
     * displayed again.
     * @param tetromino The falling tetromino to display.
     */
    void displayTetromino(Tetromino tetromino);
    /**
     * Called when the board's state is updated and so need to be displayed
     * again.
     * @param board The board to display.
     */
    void displayBoard(Board board);
    /**
     * Called when game is stopped: displays the result.
     */
    void displayGameOver();
}