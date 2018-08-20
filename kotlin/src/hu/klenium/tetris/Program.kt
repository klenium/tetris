package hu.klenium.tetris

import hu.klenium.tetris.logic.Command.*
import hu.klenium.tetris.logic.TetrisGame
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.view.MainApplication
import javafx.scene.input.KeyCode.*

fun main(args: Array<String>) {
    MainApplication.init { window ->
        val gridSize = Dimension(11, 16)
        val frame = window.createFrame(gridSize, squareSize = 30)
        val game = TetrisGame(gridSize, fallingSpeed = 700)
        frame.onKeyPress += { key -> when (key) {
                W -> game.handleCommand(ROTATE)
                A -> game.handleCommand(MOVE_LEFT)
                S -> game.handleCommand(MOVE_DOWN)
                D -> game.handleCommand(MOVE_RIGHT)
            SPACE -> game.handleCommand(DROP)
             else -> {}
        }}
        game.tetrominoStateChange += frame::displayTetromino
        game.boardStateChange += frame::displayBoard
        game.gameOverEvent += frame::displayGameOver
        game.start()
    }
}