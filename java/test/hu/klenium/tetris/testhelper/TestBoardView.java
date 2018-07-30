package hu.klenium.tetris.testhelper;

import hu.klenium.tetris.view.BoardCell;
import hu.klenium.tetris.view.BoardView;

public class TestBoardView implements BoardView {
    private BoardCell[][] lastState;
    public void update(BoardCell[][] board) {
        lastState = board;
    }
    public BoardCell[][] getData() {
        return lastState;
    }
}
