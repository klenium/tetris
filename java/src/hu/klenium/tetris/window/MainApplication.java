package hu.klenium.tetris.window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Manages the JavaFX Application. The application is created by calling
 * {@link #init(Runnable)}. At first, the window is empty, a new game must call
 * {@link #createFrame()} to add elements.
 */
public class MainApplication extends Application {
    /**
     * A task called when the application is ready to use.
     */
    private static Runnable readyTask;
    /**
     * The application's scene. It stores all GUI elements.
     */
    private static Scene scene;
    /**
     * Initializes the application. When it's doen, calls {@code task}.
     * @param task Called when the application is ready to use.
     */
    public static void init(Runnable task) {
        readyTask = task;
        launch();
    }
    /**
     * Creates a new frame that a new game can use to display it's state.
     * @return
     */
    public static GameFrame createFrame() {
        return new GameFrame(scene);
    }
    /**
     * Automatically called when the application is ready.
     * Cunfigures the window's common properties.
     * When done, calls {@code readyTask}.
     */
    @Override public void start(Stage primaryStage) {
        HBox root = new HBox();
        root.setStyle("-fx-background-color: rgb(23, 23, 23);");
        scene = new Scene(root);
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        root.requestFocus();
        readyTask.run();
    }
}