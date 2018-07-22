package hu.klenium.tetris;

import hu.klenium.tetris.window.MainApplication;

/**
 *
 */
public class Main {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MainApplication.init(() -> {
            TetrisGame game = new TetrisGame();
            game.start();
        });
    }
}