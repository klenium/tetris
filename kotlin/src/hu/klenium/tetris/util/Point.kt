package hu.klenium.tetris.util

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }
}