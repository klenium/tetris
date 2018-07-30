package hu.klenium.tetris.view;

import hu.klenium.tetris.TetrominoPart;
import hu.klenium.tetris.view.window.GameFrame;

/**
 * View of the falling tetromino: when the data is updated, its parts are displayed again.
 */
public class GraphicTetrominoView extends CanvasView implements TetrominoView {
    /**
     * Sets up the view. The parameters are used to pass needed data that will be used later.
     * @param frame The game frame which the view is connected to. It is used to access
     *              {@code Canvas#fallingTetromino}, the tetromino's drawing surface.
     * @param squareSize Size of cells.
     */
    public GraphicTetrominoView(GameFrame frame, int squareSize) {
        super(frame.getTetrominoCanvas(), squareSize);
    }
    /**
     * Clears the canvas, and displays the tetromino.
     * @param data The tetromino's cells.
     * @param baseX The tetromino's base X coordinate (left side).
     * @param baseY The tetromino's base Y coordinate (top side).
     */
    public void update(TetrominoPart[] data, int baseX, int baseY) {
        clear();
        for (TetrominoPart part : data) {
            int x = baseX + part.getOffsetX();
            int y = baseY + part.getOffsetY();
            part.getView().update(context, x, y, squareSize);
        }
    }
}