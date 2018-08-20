package hu.klenium.tetris.logic.tetromino

import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point

fun getTetrominoData(type: Int): Array<TetrominoData> {
    val masks = rawData[type]
    return Array(masks.size) { rotation ->
        val mask = masks[rotation]
        val height = mask.size
        val width = mask[0].length
        // This is just to demonstrate how to create non-null array with fixed length
        // when not all iterations of the generator loop return a new element.
        // Performance is very similar to when using arrayOfNull() - common iteration
        // logic - .requireNonNulls().
        var nextColumn = 0
        var nextRow = 0
        TetrominoData(Array(4) {
            var currentColumn = nextColumn
            var currentRow = nextRow
            verzical@while (currentRow < height) {
                horizontal@while (currentColumn < width) {
                    if (mask[currentRow][currentColumn] != ' ') {
                        nextColumn = currentColumn + 1
                        nextRow = currentRow
                        break@verzical
                    }
                    ++currentColumn
                }
                ++currentRow
                currentColumn = 0
            }
            Point(currentColumn, currentRow)
        }, Dimension(width, height))
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