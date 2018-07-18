package hu.klenium.tetris.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    private static Runnable windowReadyTask;
    private static Canvas boardCanvas;
    private static Canvas tetrominoCanvas;
    public Canvas getBoardCanvas() {
        return boardCanvas;
    }
    public Canvas getTetrominoCanvas() {
        return tetrominoCanvas;
    }
    public void init(Runnable windowReadyTask) {
        MainWindow.windowReadyTask = windowReadyTask;
        launch();
    }
    @Override public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Window.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Tetris");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
            root.requestFocus();
            boardCanvas = (Canvas) scene.lookup("#fallingTetromino");
            tetrominoCanvas = (Canvas) scene.lookup("#board");
            if (boardCanvas == null || tetrominoCanvas == null)
                throw new IOException("The layout doesn't contain the required canvases.");
            windowReadyTask.run();
        } catch (IOException e) {
            System.err.println("Failed to load the main window.\n" + e);
            System.exit(1);
        }
    }
}