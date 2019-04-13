package hu.klenium.tetris;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.view.GameFrame;
import hu.klenium.tetris.view.MainApplication;
import javafx.scene.input.KeyCode;

import java.util.EnumMap;
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
        Dimension gridSize = new Dimension(11, 16);
        int blockSize = 30;
        int fallingSpeed = 700;
        MainApplication.init(gridSize, blockSize, (GameFrame frame) -> {
            TetrisGame game = new TetrisGame(gridSize, frame, fallingSpeed);
            registerDefaultControls(game, frame);
            game.start();
        });
        MainApplication.launch();
    }
    public static void registerDefaultControls(TetrisGame game, GameFrame frame) {
        Map<KeyCode, Command> controls = new EnumMap<>(KeyCode.class);
        controls.put(KeyCode.W, Command.ROTATE);
        controls.put(KeyCode.A, Command.MOVE_LEFT);
        controls.put(KeyCode.S, Command.MOVE_DOWN);
        controls.put(KeyCode.D, Command.MOVE_RIGHT);
        controls.put(KeyCode.SPACE, Command.DROP);
        frame.registerEventListeners(game, controls);
    }
}