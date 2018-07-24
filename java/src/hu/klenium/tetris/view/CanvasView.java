package hu.klenium.tetris.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Helper class for using a JavaFX canvas.
 */
public abstract class CanvasView {
    /**
     * The context of the canvas where views can draw.
     */
    protected final GraphicsContext context;
    /**
     * The square's size, used by derived classes.
     */
    protected final int squareSize;
    /**
     * The canvas's (and thus its context's) width.
     */
    private final int contextWidth;
    /**
     * The canvas's (and thus its context's) height.
     */
    private final int contextHeight;
    /**
     * Initializes its fields for later use.
     * @param canvas A canvas used to draw in.
     * @param squareSize The square's size, used by derived classes.
     */
    public CanvasView(Canvas canvas, int squareSize) {
        context = canvas.getGraphicsContext2D();
        contextWidth = (int) canvas.getWidth();
        contextHeight = (int) canvas.getHeight();
        this.squareSize = squareSize;
    }
    /**
     * Clears the canvas's whole context.
     */
    public void clear() {
        context.clearRect(0, 0, contextWidth, contextHeight);
    }
}