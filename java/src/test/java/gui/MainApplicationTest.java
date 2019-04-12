package gui;

import hu.klenium.tetris.Main;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.view.MainApplication;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class MainApplicationTest extends MainApplication {
    @Start public void startFxTest(Stage primaryStage) {
        init(new Dimension(11, 16), 30, Main.initializeGame());
        start(primaryStage);
    }
    @Test void testCanvasAfterFirstFall(FxRobot robot) {
        //Platform.runLater(() -> {
            int i = 0;
            //robot.sleep(i * 700 + 350);
            int j = 1;
            robot.press(KeyCode.D);
            Canvas canvas = robot.lookup("#tetrominoCanvas").queryAs(Canvas.class);
            Image snap = canvas.snapshot(null, null);
            PixelReader pixelReader = snap.getPixelReader();
            Color color = pixelReader.getColor((5 + j) * 30 + 15, i * 30 + 15);
            assertNotEquals(color, Color.rgb(23, 23, 23));
        //});
    }
}
