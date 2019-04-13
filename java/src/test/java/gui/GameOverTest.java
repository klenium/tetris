package gui;

import helpers.TestMainApplication;
import hu.klenium.tetris.util.Point;
import hu.klenium.tetris.view.GraphicGameFrame;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class GameOverTest extends TestMainApplication {
    @Start private void startFxTest(Stage primaryStage) {
        setUp(primaryStage);
    }
    @Test public void checkStateAtGameOver(FxRobot robot) {
        bindParams(robot, "#boardCanvas");
        hitKey(KeyCode.SPACE);
        takePicture();
        assertBlockColorEquals(new Point(3, 6), GraphicGameFrame.tetrominoColors.get(3));
        hitKey(KeyCode.SPACE);
        bindParams(robot, "#tetrominoCanvas");
        takePicture();
        assertAllGridColorsEqual(new String[] {
            ".....",
            ".....",
            ".....",
            ".....",
            ".....",
            ".....",
            "....."
        });
        bindParams(robot, "#boardCanvas");
        takePicture();
        assertBlockColorNotEquals(new Point(3, 6), GraphicGameFrame.tetrominoColors.get(3));
    }
}