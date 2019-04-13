package gui;

import hu.klenium.tetris.Main;
import hu.klenium.tetris.view.MainApplication;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(ApplicationExtension.class)
class MainTest extends MainApplication {
    @Start public void startFxTest(Stage primaryStage) {
        Main.initGame();
        start(primaryStage);
    }
    @Test
    void testCanvasAfterFirstFall(FxRobot robot) {
        robot.press(KeyCode.E);
        Platform.runLater(() -> {
            int i = 0;
            //robot.sleep(i * 700 + 350);
            int j = 1;
            robot.press(KeyCode.D);
            Canvas canvas = robot.lookup("#tetrominoCanvas").queryAs(Canvas.class);
            WritableImage snap = canvas.snapshot(null, null);
            PixelReader pixelReader = snap.getPixelReader();
            Color color = pixelReader.getColor((5 + j) * 30 + 15, i * 30 + 15);
            assertNotEquals(color, Color.rgb(23, 23, 23));
        });
    }
}