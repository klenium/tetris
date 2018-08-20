package hu.klenium.tetris.view

import hu.klenium.tetris.logic.board.Board
import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Event
import hu.klenium.tetris.util.Point
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color

class GraphicGameFrame(
        scene: Scene,
        private val gridSize: Dimension,
        private val blockSize: Int
    ) {
    private val boardContext: GraphicsContext
    private val tetrominoContext: GraphicsContext
    private val squareSize get() = blockSize.toDouble()
    private val width get() = gridSize.width * squareSize
    private val height get() = gridSize.height * squareSize

    val onKeyPress = Event<KeyCode>()

    init {
        val pane = StackPane()
        val boardCanvas = Canvas()
        val tetrominoCanvas = Canvas()
        boardCanvas.width = width
        boardCanvas.height = height
        tetrominoCanvas.width = width
        tetrominoCanvas.height = height
        boardContext = boardCanvas.graphicsContext2D
        tetrominoContext = tetrominoCanvas.graphicsContext2D
        pane.children.addAll(boardCanvas, tetrominoCanvas)
        (scene.root as HBox).children.add(pane)
        scene.window.sizeToScene()
        scene.addEventHandler(KeyEvent.KEY_PRESSED) { event ->
            onKeyPress(event.code)
        }
    }

    fun displayTetromino(tetromino: Tetromino) {
        tetrominoContext.clearRect(0.0, 0.0, width, height)
        for (partOffset in tetromino.parts) {
            val position = tetromino.position + partOffset
            drawSquare(tetrominoContext, tetrominoColor, position)
        }
    }
    fun displayBoard(board: Board) {
        for (x in 0 until gridSize.width) {
            for (y in 0 until gridSize.height) {
                val cell = board.grid[x][y]
                val fillColor = if (!cell) backgroundColor else tetrominoColor
                drawSquare(boardContext, fillColor, Point(x, y))
            }
        }
    }
    fun displayGameOver() {
        tetrominoContext.clearRect(0.0, 0.0, width, height)
        boardContext.fill = backgroundColor
        boardContext.globalAlpha = 0.7
        boardContext.fillRect(0.0, 0.0, width, height)
    }

    private fun drawSquare(context: GraphicsContext, color: Color, position: Point) {
        context.fill = color
        context.fillRect(position.x * squareSize, position.y * squareSize, squareSize, squareSize)
    }
    private val tetrominoColor = Color.rgb(250, 250, 130)
    private val backgroundColor = Color.rgb(23, 23, 23)
}