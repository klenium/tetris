package hu.klenium.tetris.logic.tetromino;

import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;

/**
 * Helper data structure that stores all informations of a tetromino's parts in a
 * specificed rotation.
 */
public class TetrominoData {
    /**
     * This list specifies the parts of a tetromino, ie. the cells in its
     * grid. Not all cells are used by the tetromino inside the rectangle of
     * its bounding box, since each type of tetromino has different shape.
     * This list includes only the used coordinates.
     */
    public final Point[] parts;
    /**
     * The bounding box specified by the tetromino's parts. Each cells are
     * located inside this rectangle.
     */
    public final Dimension boundingBox;
    /**
     * Stores a tetromino's data in a specified rotation.
     * @param parts List of the tetromino's parts.
     * @param boundingBox Width/height of this data.
     */
    public TetrominoData(Point[] parts, Dimension boundingBox) {
        this.parts = parts;
        this.boundingBox = boundingBox;
    }
}