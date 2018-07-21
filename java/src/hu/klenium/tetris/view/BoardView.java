package hu.klenium.tetris.view;

import hu.klenium.tetris.BoardCell;
import hu.klenium.tetris.window.GameFrame;

public class BoardView extends CanvasView {
    public BoardView(GameFrame frame, int squareSize) {
        super(frame.getBoardCanvas(), squareSize);
    }
    public void update(BoardCell[][] data) {
        clear();
        for (BoardCell[] rows : data) {
            for (BoardCell cell : rows)
                cell.update(context, squareSize);
        }
    }
}