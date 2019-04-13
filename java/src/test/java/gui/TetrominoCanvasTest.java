package gui;

import helpers.TestMainApplication;
import hu.klenium.tetris.util.Point;
import hu.klenium.tetris.view.GraphicGameFrame;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class TetrominoCanvasTest extends TestMainApplication {
    @Start private void startFxTest(Stage primaryStage) {
        setUp(primaryStage);
    }
    @Test public void checkFullTetrominoCanvas(FxRobot robot) {
        bindParams(robot, "#tetrominoCanvas");
        takePicture();
        assertAllGridColorsEqual(new String[] {
            "...3.",
            "...3.",
            "..33.",
            ".....",
            ".....",
            ".....",
            "....."
        });
    }
    @Test public void checkColors(FxRobot robot) {
        bindParams(robot, "#tetrominoCanvas");
        // J: #3
        takePicture();
        assertBlockColorEquals(new Point(3, 0), GraphicGameFrame.tetrominoColors[3]);
        hitKey(KeyCode.D);
        hitKey(KeyCode.SPACE);

        // T: #6
        takePicture();
        assertBlockColorEquals(new Point(2, 0), GraphicGameFrame.tetrominoColors[6]);
        hitKey(KeyCode.SPACE);

        // I: #1
        takePicture();
        assertBlockColorEquals(new Point(2, 0), GraphicGameFrame.tetrominoColors[1]);
        hitKey(KeyCode.A);
        hitKey(KeyCode.A);
        hitKey(KeyCode.SPACE);

        // O: #0
        takePicture();
        assertBlockColorEquals(new Point(2, 0), GraphicGameFrame.tetrominoColors[0]);
    }
    @Test public void checkTetrominoMovementEffect(FxRobot robot) {
        bindParams(robot, "#tetrominoCanvas");
        takePicture();
        assertGridBlockColorsEqual(new String[] {
            "...3.",
            "...3.",
            "..33.",
            ".....",
            ".....",
            ".....",
            "....."
        });
        hitKey(KeyCode.S);
        takePicture();
        assertGridBlockColorsEqual(new String[] {
            ".....",
            "...3.",
            "...3.",
            "..33.",
            ".....",
            ".....",
            "....."
        });
        hitKey(KeyCode.W);
        takePicture();
        assertGridBlockColorsEqual(new String[] {
            ".....",
            "..333",
            "....3",
            ".....",
            ".....",
            ".....",
            "....."
        });
    }
}