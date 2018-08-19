package hu.klenium.tetris.util

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }
    operator fun plus(size: Dimension) : Point {
        return Point(this.x + size.width, this.y + size.height)
    }
    operator fun compareTo(other: Point): Int {
        return if (this.x < other.x || this.y < other.y)
            -1
        else if (this.x > other.x || this.y > other.y)
            1
        else
            0
    }
    operator fun compareTo(size: Dimension): Int {
        return if (this.x < size.width || this.y < size.height)
            -1
        else if (this.x > size.width || this.y > size.height)
            1
        else
            0
    }
}