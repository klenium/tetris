package hu.klenium.tetris.logic.tetromino

import hu.klenium.tetris.logic.board.Board
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point

class Tetromino(val type: Int, private val board: Board) {
    val parts: Array<Point>
        get() = _parts[rotation].parts
    val boundingBox: Dimension
        get() = _parts[rotation].boundingBox
    var position: Point = Point(0, 0)
        private set
    private var rotation: Int = 0
        set(value) { field = value % _parts.size }
    private val _parts: Array<TetrominoData> = getTetrominoData(type)

    fun moveToInitialPos() : Boolean {
        val centerOfBoard = board.size.width / 2.0
        val halfTetrominoWidth = boundingBox.width / 2.0
        val centeredTetrominoPosX = Math.ceil(centerOfBoard - halfTetrominoWidth).toInt()
        return tryPush(Point(centeredTetrominoPosX, 0));
    }
    fun rotateRight(): Boolean {
        val oldRotation = rotation
        ++rotation
        var canRotate = false
        if (canPushBy(Point(0, 0)))
            canRotate = true
        else {
            for (i in 1 until boundingBox.width) {
                canRotate = tryPush(Point(-i, 0))
                if (canRotate)
                    break
            }
        }
        if (!canRotate)
            rotation = oldRotation
        return canRotate
    }
    fun moveLeft(): Boolean {
        return tryPush(Point(-1, 0))
    }
    fun moveDown(): Boolean {
        return tryPush(Point(0, 1))
    }
    fun moveRight(): Boolean {
        return tryPush(Point(1, 0))
    }

    private fun tryPush(delta: Point) : Boolean {
        val canSlide = canPushBy(delta)
        if (canSlide)
            position += delta
        return canSlide
    }
    private fun canPushBy(delta: Point) : Boolean {
        return board.canAddTetromino(this, position + delta)
    }
}