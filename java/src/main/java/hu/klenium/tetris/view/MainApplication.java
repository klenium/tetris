package hu.klenium.tetris.view;

import hu.klenium.tetris.util.Dimension;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Manages the JavaFX Application.
 *
 * The application is created by calling {@link #init(Dimension, int, Consumer)}.
 */
public class MainApplication extends Application {
    /**
     * A task called when the application is ready to use.
     */
    private static Consumer<GameFrame> readyTask;
    protected static Dimension gridSize;
    protected static int blockSize;
    /**
     * Initializes the application, when done, calls {@code task}.
     * @param gridSize Number of rows and columns of the game board's grid.
     * @param blockSize The width/height of one cell in the game's board.
     * @param task Called when the application is ready to use.
     */
    public static void init(Dimension gridSize, int blockSize, Consumer<GameFrame> task) {
        MainApplication.gridSize = gridSize;
        MainApplication.blockSize = blockSize;
        readyTask = task;
    }
    public static void launch() {
        Application.launch();
    }
    /**
     * Automatically called when the application is ready.
     * Cunfigures the window's common properties.
     * When done, calls {@code readyTask}.
     * @param primaryStage Created by JavaFX platform, each GUI element's
     *                     parent. Used to show the main window.
     */
    @Override public void start(Stage primaryStage) {
        HBox root = new HBox();
        root.setStyle("-fx-background-color: rgb(23, 23, 23);");
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        root.requestFocus();
        GameFrame frame = new GraphicGameFrame(scene, gridSize, blockSize);
        readyTask.accept(frame);
    }
}