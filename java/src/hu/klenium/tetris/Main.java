package hu.klenium.tetris;

import hu.klenium.tetris.view.window.MainApplication;

/**
 * Entry point of the Java programme.
 */
public class Main {
    /**
     * Starts one new Tetris game.
     * @param args Command line-arguments, should be empty.
     */
    public static void main(String[] args) {
        MainApplication.init(() -> {
            TetrisGame game = new TetrisGame(11, 16);
            game.start();
        });
    }
}