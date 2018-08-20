package hu.klenium.tetris.util

data class Point(val x: Int, val y: Int) {
    constructor(size: Dimension) : this(size.width, size.height)
    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }
    operator fun plus(size: Dimension) : Point {
        return Point(this.x + size.width, this.y + size.height)
    }
    infix fun notSmallerThan(other: Point) : Boolean {
        return this.x >= other.x && this.y >= other.y
    }
    infix fun notGreaterThan(other: Point) : Boolean {
        return this.x <= other.x && this.y <= other.y
    }
}