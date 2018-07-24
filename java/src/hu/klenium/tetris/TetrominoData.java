package hu.klenium.tetris;

/**
 * Helper data structure that stores all informations of a tetromino's parts in a
 * specificed rotation.
 */
public class TetrominoData {
    private final TetrominoPart[] parts;
    private final int width;
    private final int height;
    /**
     * @param parts The parts/cells of the tetromino in a rotation.
     * @param width The width of this data's bounding box.
     * @param height The height of this data's bounding box.
     */
    public TetrominoData(TetrominoPart[] parts, int width, int height) {
        this.parts = parts;
        this.width = width;
        this.height = height;
    }
    public TetrominoPart[] getParts() {
        return parts;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}