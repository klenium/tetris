package gui;

import helpers.TestMainApplication;
import hu.klenium.tetris.logic.Command;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class KeyPressTest extends TestMainApplication {
    @Start private void startFxTest(Stage primaryStage) {
        setUp(primaryStage);
    }
    @Test public void sendCommandWnehKeyPressed(FxRobot robot) {
        bindParams(robot, "");
        Command recievedCommand;
        hitKey(KeyCode.W);
        recievedCommand = game.getLastCommand();
        assertEquals(Command.ROTATE, recievedCommand);
        hitKey(KeyCode.A);
        recievedCommand = game.getLastCommand();
        assertEquals(Command.MOVE_LEFT, recievedCommand);
        hitKey(KeyCode.S);
        recievedCommand = game.getLastCommand();
        assertEquals(Command.MOVE_DOWN, recievedCommand);
        hitKey(KeyCode.D);
        recievedCommand = game.getLastCommand();
        assertEquals(Command.MOVE_RIGHT, recievedCommand);
        hitKey(KeyCode.SPACE);
        recievedCommand = game.getLastCommand();
        assertEquals(Command.DROP, recievedCommand);
    }
    @Test public void unusedKeyPress(FxRobot robot) {
        bindParams(robot, "");
        Command recievedCommand;
        hitKey(KeyCode.W);
        recievedCommand = game.getLastCommand();
        assertEquals(Command.ROTATE, recievedCommand);
        recievedCommand = game.getLastCommand();
        assertNull(recievedCommand);
        hitKey(KeyCode.Q);
        recievedCommand = game.getLastCommand();
        assertNull(recievedCommand);
        hitKey(KeyCode.ENTER);
        recievedCommand = game.getLastCommand();
        assertNull(recievedCommand);
    }
}