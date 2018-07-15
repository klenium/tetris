package hu.klenium.tetris.view;

import hu.klenium.tetris.window.MainWindow;

public class TetrominoView extends CanvasView {
    public TetrominoView(MainWindow window, int squareSize) {
        super(window.getTetrominoCanvas(), squareSize);
    }
    public void update(SquareView[][] data, int baseX, int baseY) {
        clear();
        int height = data.length;
        int width = data[0].length;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (data[i][j] != null)
                    data[i][j].update(context, baseX + j, baseY + i, squareSize);
            }
        }
    }
}