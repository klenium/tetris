package hu.klenium.tetris;

import hu.klenium.tetris.window.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.init(() -> {
            TetrisGame game = TetrisGame.createNew(window);
            game.start();
        });
    }
}