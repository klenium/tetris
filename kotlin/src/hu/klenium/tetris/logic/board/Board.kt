package hu.klenium.tetris.logic.board

import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point

class Board(val size: Dimension) {
    var grid: Array<BooleanArray> = Array(size.width) { BooleanArray(size.height)}
        private set

    fun addTetromino(tetromino: Tetromino) {
        for (partOffset in tetromino.parts) {
            val (x, y) = tetromino.position + partOffset
            grid[x][y] = true
        }
    }
    fun canAddTetromino(tetromino: Tetromino, from: Point): Boolean {
        if (!isBoxInsideGrid(from, tetromino.boundingBox))
            return false
        for (partOffset in tetromino.parts) {
            val (x, y) = from + partOffset
            if (grid[x][y])
                return false
        }
        return true
    }
    fun removeFullRows(startRow: Int, numOfRowsToCheck: Int) {
        for (currentRow in startRow until (startRow + numOfRowsToCheck)) {
            if (isRowFull(currentRow))
                moveDownBoardsUpperPart(currentRow)
        }
    }

    private fun isBoxInsideGrid(boxPosition: Point, boxSize: Dimension): Boolean {
        return boxPosition.x >= 0
            && boxPosition.y >= 0
            && (boxPosition.x + boxSize.width) <= size.width
            && (boxPosition.y + boxSize.height) <= size.height
    }
    private fun isRowFull(rowIndex: Int) : Boolean {
        for (x in 0 until size.width) {
            if (!grid[x][rowIndex])
                return false
        }
        return true
    }
    private fun moveDownBoardsUpperPart(startRowIndex: Int) {
        for (y in startRowIndex downTo 1) {
            for (x in 0 until size.width)
                grid[x][y] = grid[x][y - 1]
        }
        for (x in 0 until size.width)
            grid[x][0] = false
    }
}