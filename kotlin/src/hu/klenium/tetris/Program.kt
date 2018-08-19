package hu.klenium.tetris

import hu.klenium.tetris.logic.Command
import hu.klenium.tetris.logic.TetrisGame
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.view.MainApplication
import javafx.scene.input.KeyCode
import java.util.*

fun main(args: Array<String>) {
    MainApplication.init {
        val gridSize = Dimension(11, 16)
        val blockSize = 30
        val fallingSpeed = 700
        val controls = HashMap<KeyCode, Command>()
        controls[KeyCode.W] = Command.ROTATE
        controls[KeyCode.A] = Command.MOVE_LEFT
        controls[KeyCode.S] = Command.MOVE_DOWN
        controls[KeyCode.D] = Command.MOVE_RIGHT
        controls[KeyCode.SPACE] = Command.DROP

        val frame = MainApplication.createFrame(gridSize, blockSize)
        val game = TetrisGame(gridSize, fallingSpeed)
        frame.registerEventListeners(game, controls)
        game.tetrominoStateChange += frame::displayTetromino
        game.boardStateChange += frame::displayBoard
        game.gameOverEvent += frame::displayGameOver
        game.start()
    }
}