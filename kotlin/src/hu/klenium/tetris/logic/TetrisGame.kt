package hu.klenium.tetris.logic

import hu.klenium.tetris.logic.board.Board
import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.*

open class TetrisGame(size: Dimension, fallingSpeed: Int) {
    private var isRunning = false
    private lateinit var fallingTetromino: Tetromino
    private val board = Board(size)
    private val gravity = PeriodicTask({ handleCommand(Command.FALL) }, fallingSpeed)

    val tetrominoStateChange = Event<Tetromino>()
    val boardStateChange = Event<Board>()
    val gameOverEvent = Signal()

    fun start() {
        isRunning = generateNextTetromino()
        if (isRunning) {
            tetrominoStateChange(fallingTetromino)
            boardStateChange(board)
            gravity.start()
        }
    }
    private fun stop() {
        isRunning = false
        gravity.stop()
        gameOverEvent()
    }

    fun handleCommand(command: Command) {
        if (!isRunning)
            return
        var stateChanged = false
        when (command) {
            Command.ROTATE -> stateChanged = rotateTetromino()
            Command.MOVE_LEFT -> stateChanged = moveTetrominoLeft()
            Command.MOVE_RIGHT -> stateChanged = moveTetrominoRight()
            Command.MOVE_DOWN -> {
                gravity.reset()
                stateChanged = moveTetrominoDown()
            }
            Command.FALL -> stateChanged = moveTetrominoDown()
            Command.DROP ->  {
                var lastMovedDown: Boolean
                do {
                    lastMovedDown = moveTetrominoDown()
                    stateChanged = stateChanged || lastMovedDown
                } while (lastMovedDown)
            }
        }
        if (isRunning && stateChanged)
            tetrominoStateChange(fallingTetromino)
    }

    private fun tetrominoLanded() {
        board.addTetromino(fallingTetromino)
        board.removeFullRows(fallingTetromino.position.y, fallingTetromino.boundingBox.height)
        boardStateChange(board)
        val tetrominoAdded = generateNextTetromino()
        if (tetrominoAdded) {
            gravity.reset()
            tetrominoStateChange(fallingTetromino)
        }
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
    private fun rotateTetromino() : Boolean {
        return fallingTetromino.rotateRight()
    }
}