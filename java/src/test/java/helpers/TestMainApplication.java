package helpers;

import hu.klenium.tetris.Main;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;
import hu.klenium.tetris.view.GameFrame;
import hu.klenium.tetris.view.GraphicGameFrame;
import hu.klenium.tetris.view.MainApplication;
import javafx.application.Platform;
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
    public void setUp(Stage primaryStage) {
        Dimension gridSize = new Dimension(5, 7);
        int blockSize = 30;
        init(gridSize, blockSize, (GameFrame frame) -> {
            int fallingSpeed = 700;
            TetrisGame game = TestTetrisGame.createDefault(gridSize, frame, fallingSpeed);
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
                WritableImage snapshot = canvas.snapshot(null, null);
                PixelReader pixelReader = snapshot.getPixelReader();
                lastImages.put(canvasId, pixelReader);
            }, null);
            Platform.runLater(task);
            task.get(); // Blocking.ed
        } catch (Exception e) {
            // Ignored.
        }
    }
    private Color getColorAt(Point point) {
        return lastImages.get(canvasId).getColor(point.x, point.y);
    }
    public void assertBlockColorEquals(Point point, Color excepted) {
        int x = point.x * blockSize + blockSize / 2;
        int y = point.y * blockSize + blockSize / 2;
        assertColorEqualsAt(new Point(x, y), excepted);
    }
    public void assertColorEqualsAt(Point point, Color excepted) {
        if (excepted.equals(Color.TRANSPARENT)) {
            /* The readed pixel color should be 0x00000000 based on the OpenJFX
             * documentation, but actually it is 0xffffffff for cleared pixels.
             * Waiting for help: https://stackoverflow.com/q/55669753/2625561 */
            excepted = Color.WHITE;
        }
        Color actual = getColorAt(point);
        assertEquals(excepted, actual);
    }
    public void assertGridBlockColorsEqual(String[] grid) {
        for (int x = 0; x < gridSize.width; ++x) {
            for (int y = 0; y < gridSize.height; ++y) {
                char cell = grid[y].charAt(x);
                Color excepted;
                if (cell == '.')
                    excepted = Color.TRANSPARENT;
                else {
                    int i = Integer.parseInt("" + cell);
                    excepted = GraphicGameFrame.tetrominoColors[i];
                }
                assertBlockColorEquals(new Point(x, y), excepted);
            }
        }
    }
    public void assertAllGridColorsEqual(String[] grid) {
        for (int x = 0; x < gridSize.width; ++x) {
            for (int y = 0; y < gridSize.height; ++y) {
                char cell = grid[y].charAt(x);
                Color excepted;
                if (cell == '.')
                    excepted = Color.TRANSPARENT;
                else {
                    int i = Integer.parseInt("" + cell);
                    excepted = GraphicGameFrame.tetrominoColors[i];
                }
                for (int i = 0; i < blockSize; ++i) {
                    for (int j = 0; j < blockSize; ++j) {
                        Point point = new Point(x * blockSize + i, y * blockSize + j);
                        assertColorEqualsAt(point, excepted);
                    }
                }
            }
        }
    }
}