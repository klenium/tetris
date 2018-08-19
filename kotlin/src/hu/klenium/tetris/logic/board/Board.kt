package hu.klenium.tetris.logic.board

import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point

class Board(size: Dimension) {
    var grid: Array<BooleanArray> = Array(size.width) { BooleanArray(size.height)}
        private set
    val size: Dimension
        get() = Dimension(grid.size, grid[0].size)

    fun addTetromino(tetromino: Tetromino) {
        for (partOffset in tetromino.parts) {
            val (x, y) = tetromino.position + partOffset
            grid[x][y] = true
        }
    }
    fun canAddTetromino(tetromino: Tetromino, from: Point): Boolean {
        return true
    }
    fun removeFullRows() {

    }

    private fun isBoxInsideGrid(boxPosition: Point, boxSize: Dimension): Boolean {
        TODO("not yet implemented")
    }
    private fun isRowFull(rowIndex: Int) : Boolean {
        TODO("not yet implemented")
    }
    private fun moveDownBoardsUpperPart(startRowIndex: Int) {

    }
}