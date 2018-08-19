package hu.klenium.tetris.logic

import hu.klenium.tetris.logic.board.Board
import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.PeriodicTask
import hu.klenium.tetris.util.random

open class TetrisGame(size: Dimension, fallingSpeed: Int) {
    protected var isRunning: Boolean = false
    protected lateinit var fallingTetromino: Tetromino
    protected val board: Board = Board(size)
    private val gravity: PeriodicTask = PeriodicTask({ handleCommand(Command.FALL) }, fallingSpeed)

    fun start() {
        isRunning = generateNextTetromino()
        if (isRunning)
            gravity.start()
    }
    private fun stop() {
        isRunning = false
        gravity.stop()
    }

    fun handleCommand(command: Command) {
        if (!isRunning)
            return
        when (command) {
            Command.ROTATE -> fallingTetromino.rotateRight()
            Command.MOVE_LEFT -> moveTetrominoLeft()
            Command.MOVE_RIGHT -> moveTetrominoRight()
            Command.MOVE_DOWN -> {
                gravity.reset()
                moveTetrominoDown()
            }
            Command.FALL -> moveTetrominoDown()
            Command.DROP -> while (moveTetrominoDown()) {}
        }
    }

    private fun tetrominoLanded() {
        board.addTetromino(fallingTetromino)
        board.removeFullRows()
        val tetrominoAdded = generateNextTetromino()
        if (tetrominoAdded)
            gravity.reset()
        else
            stop()
    }
    private fun generateNextTetromino() : Boolean {
        val type = getNextTetrominoType()
        fallingTetromino = Tetromino(type, board)
        return fallingTetromino.moveToInitialPos()
    }
    protected open fun getNextTetrominoType(): Int {
        return (0..6).random()
    }
    private fun moveTetrominoLeft() : Boolean {
        return fallingTetromino.moveLeft()
    }
    private fun moveTetrominoDown() : Boolean {
        val moved = fallingTetromino.moveDown()
        if (!moved)
            tetrominoLanded()
        return moved
    }
    private fun moveTetrominoRight() : Boolean {
        return fallingTetromino.moveRight()
    }
}