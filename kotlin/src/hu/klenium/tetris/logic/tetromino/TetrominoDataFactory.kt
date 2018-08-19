package hu.klenium.tetris.logic.tetromino

import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point

fun getTetrominoData(type: Int): Array<TetrominoData> {
    val masks = rawData[type]
    return Array(masks.size) { rotation ->
        val mask = masks[rotation]
        val height = mask.size
        val width = mask[0].length
        var partsCount = 0
        val parts = arrayOfNulls<Point>(4)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (mask[y][x] != ' ')
                    parts[partsCount++] = Point(x, y)
            }
        }
        TetrominoData(parts.requireNoNulls(), Dimension(width, height))
    }
}

private val rawData = arrayOf(
    arrayOf(
        arrayOf(
            "XX",
            "XX"
        )
    ),
    arrayOf(
        arrayOf(
            "X",
            "X",
            "X",
            "X"
        ),
        arrayOf(
            "XXXX"
        )
    ),
    arrayOf(
        arrayOf(
            "X ",
            "X ",
            "XX"
        ),
        arrayOf(
            "  X",
            "XXX"
        ),
        arrayOf(
            "XX",
            " X",
            " X"
        ),
        arrayOf(
            "XXX",
            "X  "
        )
    ),
    arrayOf(
        arrayOf(
            " X",
            " X",
            "XX"
        ),
        arrayOf(
            "XXX",
            "  X"
        ),
        arrayOf(
            "XX",
            "X ",
            "X "
        ),
        arrayOf(
            "X  ",
            "XXX"
        )
    ),
    arrayOf(
        arrayOf(
            " XX",
            "XX "
        ),
        arrayOf(
            "X ",
            "XX",
            " X"
        )
    ),
    arrayOf(
        arrayOf(
            "XX ",
            " XX"
        ),
        arrayOf(
            " X",
            "XX",
            "X "
        )
    ),
    arrayOf(
        arrayOf(
            "XXX",
            " X "
        ),
        arrayOf(
            "X ",
            "XX",
            "X "
        ),
        arrayOf(
            " X ",
            "XXX"
        ),
        arrayOf(
            " X",
            "XX",
            " X"
        )
    )
)