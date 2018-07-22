package hu.klenium.tetris.window;

import hu.klenium.tetris.TetrisGame;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import static hu.klenium.tetris.UserCommand.*;

/**
 * Creates and maintains the layout used by a game, responds to events caused by the game's player.
 */
public class GameFrame {
    /**
     * The canvas which is used to display the board.
     */
    private final Canvas boardCanvas;
    /**
     * The canvas which is used to draw the falling tetromino.
     */
    private final Canvas tetrominoCanvas;
    /**
     * The application's scene, used to create and store GUI elements, add event handlers.
     */
    private final Scene scene;
    /**
     * Gives access to the board's canvas.
     * @return The board's canvas.
     */
    public Canvas getBoardCanvas() {
        return boardCanvas;
    }
    /**
     * Gives access to the falling tetromino's canvas.
     * @return The tetromino's canvas.
     */
    public Canvas getTetrominoCanvas() {
        return tetrominoCanvas;
    }
    /**
     * Creates a new frame for a new game in the window. Builds all GUI elements used
     * by the new game, and adds them to the scene.
     * @param scene The JavaFX application's scene which is used to build/store new elements.
     */
    public GameFrame(Scene scene) {
        this.scene = scene;
        StackPane pane = new StackPane();
        boardCanvas = new Canvas();
        tetrominoCanvas = new Canvas();
        pane.getChildren().addAll(boardCanvas, tetrominoCanvas);
        ((HBox) scene.getRoot()).getChildren().add(pane);
    }
    /**
     * Adds event handler to the scene. When one of the specificed keys is pressed,
     * sends the corresponding command to {@code game}.
     * @param game The game which will recieve a command on user input.
     */
    public void registerEventListeners(TetrisGame game) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            synchronized (game) {
                switch (event.getCode()) {
                    case W: game.handleCommand(ROTATE); break;
                    case A: game.handleCommand(MOVE_LEFT); break;
                    case S: game.handleCommand(MOVE_DOWN); break;
                    case D: game.handleCommand(MOVE_RIGHT); break;
                    case SPACE: game.handleCommand(DROP); break;
                }
            }
        });
    }
    /**
     * Sets the fram's size, based on the board's size. Also resizes the window to fit
     * the content.
     * @param width The frame's width.
     * @param height The frame's height.
     */
    public void setSize(int width, int height) {
        boardCanvas.setWidth(width);
        boardCanvas.setHeight(height);
        tetrominoCanvas.setWidth(width);
        tetrominoCanvas.setHeight(height);
        scene.getWindow().sizeToScene();
    }
}