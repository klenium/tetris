package hu.klenium.tetris.util;

import java.util.Objects;

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
    @Override public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }
    @Override public int hashCode() {
        return Objects.hash(x, y);
    }
}