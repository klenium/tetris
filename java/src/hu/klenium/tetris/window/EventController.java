package hu.klenium.tetris.window;

import hu.klenium.tetris.UserCommand;
import hu.klenium.tetris.TetrisGame;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class EventController {
    @FXML public void windowKeyPressed(KeyEvent event) {
        UserCommand command;
        switch (event.getCode()) {
            case W:
                command = UserCommand.ROTATE;
                break;
            case A:
                command = UserCommand.MOVE_LEFT;
                break;
            case S:
                command = UserCommand.MOVE_DOWN;
                break;
            case D:
                command = UserCommand.MOVE_RIGHT;
                break;
            case SPACE:
                command = UserCommand.DROP;
                break;
            default:
                return;
        }
        TetrisGame.getInstance().handleCommand(command);
    }
}