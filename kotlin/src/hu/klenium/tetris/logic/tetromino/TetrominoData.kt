package hu.klenium.tetris.logic.tetromino

import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point

data class TetrominoData(val parts: Array<Point>, val boundingBox: Dimension)