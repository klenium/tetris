package hu.klenium.tetris.util;

public class Point {
    public final int x;
    public final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }
}