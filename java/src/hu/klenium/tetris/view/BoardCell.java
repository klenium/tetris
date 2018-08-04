package hu.klenium.tetris.view;

import javafx.scene.canvas.GraphicsContext;

/**
 * Board cells might store one part of a fallen tetromino.
 * <br>
 * Empty cells doesn't have view in this version, hence this class implements
 * the optional object pattern.
 */
public class BoardCell {
    /**
     * View of this board cell. When a tetromino falls down, its parts are
     * copied to the board. The view is empty (null) if a tetromino hasn't fallen
     * onto this cell, otherwise it stores the tetromino part's view.
     */
    private SquareView view = null;
    public SquareView getView() {
        return view;
    }
    public void setView(SquareView view) {
        this.view = view;
    }
    /**
     * @return False, if this cell contains a tetromino part (has a view),
     *         true if it's unused.
     */
    public boolean isEmpty() {
        return view == null;
    }
    /**
     * If the cell is used, displays its view.
     * @param context The context used by the view to draw.
     * @param posX X coordinate (from the board's left side).
     * @param posY Y coordinate (from the board's top side).
     * @param size Size of the cell.
     */
    public void update(GraphicsContext context, int posX, int posY, int size) {
        if (!isEmpty())
            view.update(context, posX, posY, size);
    }
}