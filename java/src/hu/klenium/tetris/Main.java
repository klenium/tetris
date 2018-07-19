package hu.klenium.tetris;

import hu.klenium.tetris.window.MainApplication;

public class Main {
    public static void main(String[] args) {
        MainApplication.init(() -> {
            TetrisGame game = TetrisGame.createNew();
            game.start();
        });
    }
}