package hu.klenium.tetris;

public class TetrominoData {
    private final TetrominoPart[] parts;
    private final int width;
    private final int height;
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