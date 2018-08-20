
import helpers.TestUtil
import hu.klenium.tetris.logic.Command
import hu.klenium.tetris.logic.TetrisGame
import hu.klenium.tetris.logic.board.Board
import hu.klenium.tetris.logic.tetromino.Tetromino
import hu.klenium.tetris.util.Dimension
import hu.klenium.tetris.util.Point
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val PERIOD : Long = 500
private const val OFFSET : Long = PERIOD / 2

class TetrisGameTest {
    private var game: TetrisGame
    private lateinit var tetromino: Tetromino
    private lateinit var board: Board
    init {
        game = object: TetrisGame(Dimension(5, 6), PERIOD.toInt()) {
            init {
                onTetrominoStateChange += { t-> tetromino = t }
                onBoardStateChange += { b -> board = b }
            }
            //                                  J, T, I, O
            private val tetrominoTypes = listOf(3, 6, 1, 0)
            private var currentIndex = 0
            override fun getNextTetrominoType(): Int {
                return tetrominoTypes[currentIndex++]
            }
        }
    }
    @Test fun checkFalling() {
        game.start()
        assertEquals(tetromino.position, Point(2, 0))
        Thread.sleep(OFFSET + PERIOD)
        assertEquals(tetromino.position, Point(2, 1))
        Thread.sleep(PERIOD)
        assertEquals(tetromino.position, Point(2, 2))
        Thread.sleep(PERIOD)
        assertEquals(tetromino.position, Point(2, 3))
    }
    @Test fun checlBoardSize() {
        game.start()
        assertEquals(board.size.width, 5)
        assertEquals(board.size.height, 6)
    }
    @Test fun unStartedState() {
        assertFalse(::tetromino.isInitialized)
        game.handleCommand(Command.MOVE_DOWN)
        assertFalse(::tetromino.isInitialized)
    }
    @Test fun runningState() {
        game.start()
        assertTrue(::tetromino.isInitialized)
    }
    @Test fun stoppedState() {
        game.start()
        TestUtil.controlTetromino(game, " ")
        TestUtil.controlTetromino(game, " ")
        val tetromino1 = tetromino
        TestUtil.controlTetromino(game, " ")
        val tetromino2 = tetromino
        assertSame(tetromino1, tetromino2)
        assertEquals(tetromino1.position, tetromino2.position)
    }
    @Test fun controlTetromino() {
        game.start()
        val position1 = tetromino.position
        game.handleCommand(Command.MOVE_LEFT)
        val position2 = tetromino.position
        assertNotEquals(position1, position2)
    }
    @Test fun tetrominoLanded() {
        game.start()
        val tetromino1 = tetromino
        game.handleCommand(Command.DROP)
        val tetromino2 = tetromino
        assertNotSame(tetromino1, tetromino2)
        assertEquals(tetromino2.type, 6)
        TestUtil.checkBoardState(board, arrayOf(
            ".....",
            ".....",
            ".....",
            "...#.",
            "...#.",
            "..##."
        ))
    }
    @Test fun removeFullRows() {
        game.start()
        TestUtil.controlTetromino(game, "DD ")
        TestUtil.controlTetromino(game, "WWA ")
        TestUtil.checkBoardState(board, arrayOf(
            ".....",
            ".....",
            ".....",
            ".....",
            "....#",
            ".#..#"
        ))
    }
    @Test fun initialPosition() {
        game.start()
        assertEquals(tetromino.position, Point(2, 0))
        TestUtil.controlTetromino(game, "D ")
        assertEquals(tetromino.position, Point(1, 0))
        TestUtil.controlTetromino(game, " ")
        assertEquals(tetromino.position, Point(2, 0))
    }
}