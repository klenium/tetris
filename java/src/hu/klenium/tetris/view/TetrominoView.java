package hu.klenium.tetris.view;

import hu.klenium.tetris.TetrominoPart;

public interface TetrominoView {
    void update(TetrominoPart[] data, int baseX, int baseY);
    void clear();
}