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
public class BoardCanvasTest extends TestMainApplication {
    @Start private void startFxTest(Stage primaryStage) {
        setUp(primaryStage);
    }
    @Test public void unchangedWhenTetrominoMoved(FxRobot robot) {
        bindParams(robot, "#boardCanvas");
        takePicture();
        assertBlockColorEquals(new Point(3, 1), GraphicGameFrame.backgroundColor);
        hitKey(KeyCode.S);
        assertBlockColorEquals(new Point(3, 1), GraphicGameFrame.backgroundColor);
        bindParams(robot, "#tetrominoCanvas");
        takePicture();
        assertBlockColorEquals(new Point(3, 1), GraphicGameFrame.tetrominoColors[3]);
    }
    @Test public void changesWhenTetrominoLanded(FxRobot robot) {
        bindParams(robot, "#boardCanvas");
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        takePicture();
        assertBlockColorEquals(new Point(3, 4), GraphicGameFrame.tetrominoColors[3]);
    }
    @Test public void changesWhenRowsRemoved(FxRobot robot) {
        bindParams(robot, "#boardCanvas");
        /*
          0 1 2 3 4
        0 . . . . .
        1 . . . . .
        2 . . . T .
        3 I . . T T
        4 I . . T J
        5 I O O . J
        6 I O O J J
        */

        // J: #3
        hitKey(KeyCode.D);
        hitKey(KeyCode.SPACE);

        // T: #6
        hitKey(KeyCode.W);
        hitKey(KeyCode.D);
        hitKey(KeyCode.D);
        hitKey(KeyCode.SPACE);

        // I: #1
        hitKey(KeyCode.A);
        hitKey(KeyCode.A);
        hitKey(KeyCode.SPACE);

        // O: #0
        hitKey(KeyCode.A);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);
        hitKey(KeyCode.S);

        takePicture();
        assertBlockColorEquals(new Point(0, 3), GraphicGameFrame.tetrominoColors[1]);
        assertBlockColorEquals(new Point(3, 5), GraphicGameFrame.backgroundColor);
        assertBlockColorEquals(new Point(3, 2), GraphicGameFrame.tetrominoColors[6]);
        assertBlockColorEquals(new Point(4, 3), GraphicGameFrame.tetrominoColors[6]);
        assertBlockColorEquals(new Point(4, 4), GraphicGameFrame.tetrominoColors[3]);
        assertBlockColorEquals(new Point(3, 6), GraphicGameFrame.tetrominoColors[3]);

        /*
          0 1 2 3 4
        0 . . . . .
        1 . . . . .
        2 . . .[.].
        3[.]. . T[.]
        4 I . . T[T]
        5 I[.|.|T]J
        6 I O O[.]J
        */
        hitKey(KeyCode.S);
        takePicture();
        assertBlockColorEquals(new Point(0, 3), GraphicGameFrame.backgroundColor);
        assertBlockColorEquals(new Point(3, 5), GraphicGameFrame.tetrominoColors[6]);
        assertBlockColorEquals(new Point(3, 2), GraphicGameFrame.backgroundColor);
        assertBlockColorEquals(new Point(4, 3), GraphicGameFrame.backgroundColor);
        assertBlockColorEquals(new Point(4, 4), GraphicGameFrame.tetrominoColors[6]);
        assertBlockColorEquals(new Point(3, 6), GraphicGameFrame.backgroundColor);
    }
}