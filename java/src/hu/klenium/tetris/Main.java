package hu.klenium.tetris;

import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.view.GameFrame;
import hu.klenium.tetris.view.MainApplication;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Entry point of the Java program.
 */
public class Main {
    /**
     * Starts one new Tetris game.
     * @param args Command line arguments, should be empty.
     */
    public static void main(String[] args) {
        MainApplication.init(() -> {
            Dimension gridSize = new Dimension(11, 16);
            int blockSize = 30;
            GameFrame frame = MainApplication.createFrame(gridSize, blockSize);
            TetrisGame game = new TetrisGame(gridSize, frame);
            Map<KeyCode, Command> controls = new HashMap<>();
            controls.put(KeyCode.W, Command.ROTATE);
            controls.put(KeyCode.A, Command.MOVE_LEFT);
            controls.put(KeyCode.S, Command.MOVE_DOWN);
            controls.put(KeyCode.D, Command.MOVE_RIGHT);
            controls.put(KeyCode.SPACE, Command.DROP);
            frame.registerEventListeners(game, controls);
            game.start();
        });
    }
}