package hu.klenium.tetris.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A simple, general view class that draws just a filled square to a canvas.
 * Its size can be changed, the color can be selected.
 */
public class SquareView {
    /**
     * The color of the filled square.
     */
    private final Color color;
    /**
     * Creates a new square view.
     * @param type The tetromino's type, which selects its color.
     */
    public SquareView(int type) {
        this.color = colors[type];
    }
    /**
     * Draws the filled square.
     * @param context The canvas's context to draw in.
     * @param x The grid's column.
     * @param y The grid's row.
     * @param size Size of rows/columns and the square.
     */
    public void update(GraphicsContext context, int x, int y, int size) {
        context.setFill(color);
        context.fillRect(x * size, y * size, size, size);
    }

    /**
     * Colors of tetrominoes. Its index is the type of the tetromino.
     */
    private final static Color[] colors = new Color[] {
            Color.rgb(0, 200, 100),
            Color.rgb(70, 130, 0),
            Color.rgb(160, 220, 0),
            Color.rgb(250, 230, 60),
            Color.rgb(250, 180, 50),
            Color.rgb(230, 100, 0),
            Color.rgb(200, 0, 0)
    };
}