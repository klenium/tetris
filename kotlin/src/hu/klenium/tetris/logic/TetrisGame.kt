package hu.klenium.tetris.logic

import hu.klenium.tetris.logic.board.Board
import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.Dimension

open class TetrisGame(size: Dimension) {
    protected var isRunning: Boolean = false
    protected var fallingTetromino: Tetromino? = null
    protected val board: Board = Board(size)

    fun handleCommand(command: Command) {

    }

    fun start() {

    }

    protected open fun getNextTetrominoType(): Int {
        TODO("Not yet implemented")
    }
}