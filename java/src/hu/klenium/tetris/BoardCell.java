package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a cell of the board.
 * A cell can be empty, or can contain a cell from a previously fallen tetromino.
 */
public class BoardCell {
    /**
     *
     */
    private SquareView view = null;
    /**
     *
     */
    private final int posX;
    /**
     *
     */
    private final int posY;
    /**
     * Creates an empty cell.
     * @param posX X coordinate (from the board's left side).
     * @param posY Y coordinate (from the board's top side).
     */
    public BoardCell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    /**
     *
     * @return If the cell is used, returns its view, or null otherwise.
     */
    public SquareView getView() {
        return view;
    }
    /**
     *
     * @param view The cell's new view.
     */
    public void setView(SquareView view) {
        this.view = view;
    }
    /**
     * Clears the cell (so it will be empty). Removes its view.
     */
    public void clear() {
        this.view = null;
    }
    /**
     *
     * @return False, if this cell is used (has a view), true otherwise.
     */
    public boolean isEmpty() {
        return view == null;
    }
    /**
     * If the cell is used, displays its view.
     * @param context The context used by the view to draw.
     * @param size Size of the cell.
     */
    public void update(GraphicsContext context, int size) {
        if (!isEmpty())
            view.update(context, posX, posY, size);
    }
}