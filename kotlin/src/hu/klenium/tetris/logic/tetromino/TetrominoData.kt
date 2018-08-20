package hu.klenium.tetris.logic.tetromino

import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point
import java.util.Arrays

data class TetrominoData(val parts: Array<Point>, val boundingBox: Dimension) {
    override fun equals(other: Any?): Boolean {
        return (this === other)
            || (other is TetrominoData
                && Arrays.equals(parts, other.parts)
                && boundingBox == other.boundingBox)
    }
    override fun hashCode(): Int {
        return 31 * Arrays.hashCode(parts) + boundingBox.hashCode()
    }
}