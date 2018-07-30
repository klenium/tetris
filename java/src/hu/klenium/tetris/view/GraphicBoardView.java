package hu.klenium.tetris.view;

import hu.klenium.tetris.view.window.GameFrame;

/**
 * View of the board's data: when it's updated, this view displays it again.
 * <br>
 * Currently it draws only each cells, but this class can be used to add more decorations.
 */
public class GraphicBoardView extends CanvasView implements BoardView {
    /**
     * Sets up the view. The parameters are used to pass needed data that will be used later.
     * @param frame The game frame which this board is connected to. It is used to access
     *              {@code Canvas#board}, the board's drawing surface.
     * @param squareSize Size of cells.
     */
    public GraphicBoardView(GameFrame frame, int squareSize) {
        super(frame.getBoardCanvas(), squareSize);
    }
    /**
     * Displays the board's data again.
     * @param board The table of the board's cells those will be drawn.
     */
    public void update(BoardCell[][] board) {
        clear();
        for (BoardCell[] row : board) {
            for (BoardCell cell : row)
                cell.update(context, squareSize);
        }
    }
}