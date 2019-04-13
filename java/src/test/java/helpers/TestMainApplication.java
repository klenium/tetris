package helpers;

import hu.klenium.tetris.Main;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import hu.klenium.tetris.view.GameFrame;
import hu.klenium.tetris.view.GraphicGameFrame;
import hu.klenium.tetris.view.MainApplication;
import javafx.application.Platform;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

import static org.junit.jupiter.api.Assertions.*;

public class TestMainApplication extends MainApplication {
    private Map<String, PixelReader> lastImages = new HashMap<>();
    private String canvasId;
    private FxRobot robot;
    protected TestTetrisGame game;
    public void setUp(Stage primaryStage) {
        Dimension gridSize = new Dimension(5, 7);
        int blockSize = 30;
        init(gridSize, blockSize, (GameFrame frame) -> {
            int fallingSpeed = 700;
            game = TestTetrisGame.createDefault(gridSize, frame, fallingSpeed);
            Main.registerDefaultControls(game, frame);
            game.start();
        });
        super.start(primaryStage);
    }
    public void hitKey(KeyCode key) {
        robot.press(key);
        robot.release(key);
    }
    public void bindParams(FxRobot robot, String canvasId) {
        this.robot = robot;
        this.canvasId = canvasId;
    }
    public void takePicture() {
        try {
            FutureTask<?> task = new FutureTask<Void>(() -> {
                Canvas canvas = robot.lookup(canvasId).queryAs(Canvas.class);
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                WritableImage snapshot = canvas.snapshot(params, null);
                PixelReader pixelReader = snapshot.getPixelReader();
                lastImages.put(canvasId, pixelReader);
            }, null);
            Platform.runLater(task);
            task.get(); // Blocking.
        } catch (Exception e) {
            // Ignored.
        }
    }
    private Color getColorAt(Point point) {
        return lastImages.get(canvasId).getColor(point.x, point.y);
    }
    public void assertBlockColorNotEquals(Point point, Color excepted) {
        int x = point.x * blockSize + blockSize / 2;
        int y = point.y * blockSize + blockSize / 2;
        assertColorNotEquals(new Point(x, y), excepted);
    }
    public void assertBlockColorEquals(Point point, Color excepted) {
        int x = point.x * blockSize + blockSize / 2;
        int y = point.y * blockSize + blockSize / 2;
        assertColorEquals(new Point(x, y), excepted);
    }
    public void assertColorNotEquals(Point point, Color excepted) {
        Color actual = getColorAt(point);
        assertNotEquals(excepted, actual);
    }
    public void assertColorEquals(Point point, Color excepted) {
        Color actual = getColorAt(point);
        assertEquals(excepted, actual);
    }
    public void assertGridBlockColorsEqual(String[] grid) {
        for (int x = 0; x < gridSize.width; ++x) {
            for (int y = 0; y < gridSize.height; ++y) {
                char cell = grid[y].charAt(x);
                Color excepted = getColorFromCell(cell);
                assertBlockColorEquals(new Point(x, y), excepted);
            }
        }
    }
    public void assertAllGridColorsEqual(String[] grid) {
        for (int x = 0; x < gridSize.width; ++x) {
            for (int y = 0; y < gridSize.height; ++y) {
                char cell = grid[y].charAt(x);
                Color excepted = getColorFromCell(cell);
                for (int i = 0; i < blockSize; ++i) {
                    for (int j = 0; j < blockSize; ++j) {
                        Point point = new Point(x * blockSize + i, y * blockSize + j);
                        assertColorEquals(point, excepted);
                    }
                }
            }
        }
    }
    private Color getColorFromCell(char cell) {
        if (cell == '.') {
            if (canvasId.equals("#tetrominoCanvas"))
                return Color.TRANSPARENT;
            else
                return GraphicGameFrame.backgroundColor;
        }
        else {
            int i = Integer.parseInt("" + cell);
            return GraphicGameFrame.tetrominoColors.get(i);
        }
    }
}