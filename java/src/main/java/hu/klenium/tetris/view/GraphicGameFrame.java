package hu.klenium.tetris.view;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.board.BoardCell;
import hu.klenium.tetris.logic.board.FilledBoardCell;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Creates and maintains the layout used by a game, responds to events caused by the game's player.
 */
public class GraphicGameFrame implements GameFrame {
    /**
     * Context of the canvas used to draw the board.
     */
    private final GraphicsContext boardContext;
    /**
     * Context of the canvas used to draw the falling tetromino.
     */
    private final GraphicsContext tetrominoContext;
    /**
     * The application's scene, used to create and store GUI elements, add event handlers.
     */
    private final Scene scene;
    /**
     * The board's size.
     */
    private final Dimension gridSize;
    /**
     * One cell's size.
     */
    private final int blockSize;
    /**
     * Cached size of canvases. It equals to gridSize * blockSize.
     */
    private final Dimension canvasSize;

    /**
     * Creates a new frame for a new game in the window. Builds all GUI elements used
     * by the new game, and adds them to the scene.
     * @param scene The JavaFX application's scene which is used to build/store new elements.
     * @param gridSize The board's size (rows/columns).
     * @param blockSize A board/tetromino cell's size.
     */
    public GraphicGameFrame(Scene scene, Dimension gridSize, int blockSize) {
        this.scene = scene;
        this.gridSize = gridSize;
        this.blockSize = blockSize;
        this.canvasSize = new Dimension(gridSize.width * blockSize, gridSize.height * blockSize);
        StackPane pane = new StackPane();
        Canvas boardCanvas = new Canvas();
        Canvas tetrominoCanvas = new Canvas();
        boardCanvas.setId("boardCanvas");
        boardCanvas.setWidth(canvasSize.width);
        boardCanvas.setHeight(canvasSize.height);
        tetrominoCanvas.setId("tetrominoCanvas");
        tetrominoCanvas.setWidth(canvasSize.width);
        tetrominoCanvas.setHeight(canvasSize.height);
        boardContext = boardCanvas.getGraphicsContext2D();
        tetrominoContext = tetrominoCanvas.getGraphicsContext2D();
        pane.getChildren().addAll(boardCanvas, tetrominoCanvas);
        ((HBox) scene.getRoot()).getChildren().add(pane);
        scene.getWindow().sizeToScene();
    }
    /**
     * Adds event handler to the scene. When one of the specificed keys is pressed,
     * sends the corresponding command to {@code game}.
     * @param game The game which will recieve a command on user input.
     * @param keys List of keys which are used to control the falling tetromino.
     */
    public void registerEventListeners(TetrisGame game, Map<KeyCode, Command> keys) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyCode pressedKey = event.getCode();
            Command command = keys.get(pressedKey);
            if (command != null)
                game.handleCommand(command);
        });
    }

    /**
     * Called when the falling tetromino's state is updated and so need to be
     * displayed again.
     * @param tetromino The falling tetromino to display.
     */
    public void displayTetromino(Tetromino tetromino) {
        tetrominoContext.clearRect(0, 0, canvasSize.width, canvasSize.height);
        int type = tetromino.getType();
        Point base = tetromino.getPosition();
        Color color = tetrominoColors.get(type);
        for (Point partOffset : tetromino.getCurrentData().parts) {
            Point position = base.add(partOffset);
            drawSquare(tetrominoContext, color, position);
        }
    }
    /**
     * Called when the board's state is updated and so need to be displayed
     * again.
     * @param board The board to display.
     */
    public void displayBoard(Board board) {
        BoardCell[][] cells = board.getGrid();
        for (int x = 0; x < gridSize.width; ++x) {
            for (int y = 0; y < gridSize.height; ++y) {
                BoardCell cell = cells[x][y];
                Color fillColor;
                if (cell.isEmpty())
                    fillColor = backgroundColor;
                else {
                    FilledBoardCell usedCell = (FilledBoardCell) cell;
                    fillColor = tetrominoColors.get(usedCell.getType());
                }
                drawSquare(boardContext, fillColor, new Point(x, y));
            }
        }
    }
    /**
     * Called when game is stopped: displays the result.
     */
    public void displayGameOver() {
        tetrominoContext.clearRect(0, 0, canvasSize.width, canvasSize.height);
        boardContext.setFill(backgroundColor);
        boardContext.setGlobalAlpha(0.7);
        boardContext.fillRect(0, 0, canvasSize.width, canvasSize.height);
    }

    /**
     * Draws a simple filled square to a canvas.
     * Used to draw cells over the board's grid.
     * @param context The canvas' context to draw in.
     * @param color The square's fill color.
     * @param position The square's coordinate on the grid.
     */
    private void drawSquare(GraphicsContext context, Color color, Point position) {
        context.setFill(color);
        int x = position.x * blockSize;
        int y = position.y * blockSize;
        context.fillRect(x, y, blockSize, blockSize);
    }
    /**
     * Colors of tetrominoes. Its index is the type of the tetromino.
     */
    public static final List<Color> tetrominoColors =
        Collections.unmodifiableList(
            Arrays.asList(
                Color.rgb(0, 200, 100),
                Color.rgb(70, 130, 0),
                Color.rgb(160, 220, 0),
                Color.rgb(250, 230, 60),
                Color.rgb(250, 180, 50),
                Color.rgb(230, 100, 0),
                Color.rgb(200, 0, 0)
            )
        );
    /**
     * Empty cells' color.
     */
    public static final Color backgroundColor = Color.rgb(23, 23, 23);
}