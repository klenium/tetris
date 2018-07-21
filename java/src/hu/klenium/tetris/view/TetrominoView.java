package hu.klenium.tetris.view;

import hu.klenium.tetris.TetrominoPart;
import hu.klenium.tetris.window.GameFrame;

public class TetrominoView extends CanvasView {
    public TetrominoView(GameFrame frame, int squareSize) {
        super(frame.getTetrominoCanvas(), squareSize);
    }
    public void update(TetrominoPart[] data, int baseX, int baseY) {
        clear();
        for (TetrominoPart part : data) {
            int x = baseX + part.getOffsetX();
            int y = baseY + part.getOffsetY();
            part.getView().update(context, x, y, squareSize);
        }
    }
}