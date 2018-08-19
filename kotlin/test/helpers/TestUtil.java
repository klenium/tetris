package helpers;

import hu.klenium.tetris.logic.Command;
import hu.klenium.tetris.logic.TetrisGame;
import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.util.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {
    public static boolean controlTetromino(Tetromino tetromino, String commands) {
        boolean success = true;
        char[] data = commands.toCharArray();
        for (int i = 0; i < data.length && success; ++i) {
            switch (data[i]) {
                case 'W': success = tetromino.rotateRight(); break;
                case 'A': success = tetromino.moveLeft(); break;
                case 'S': success = tetromino.moveDown(); break;
                case 'D': success = tetromino.moveRight(); break;
                default: throw new RuntimeException();
            }
        }
        return success;
    }
    public static void controlTetromino(TetrisGame game, String commands) {
        for (char data : commands.toCharArray()) {
            switch (data) {
                case 'W': game.handleCommand(Command.ROTATE); break;
                case 'A': game.handleCommand(Command.MOVE_LEFT); break;
                case 'S': game.handleCommand(Command.MOVE_DOWN); break;
                case 'D': game.handleCommand(Command.MOVE_RIGHT); break;
                case ' ': game.handleCommand(Command.DROP); break;
                default: throw new RuntimeException();
            }
        }
    }
    public static void checkBoardState(Board board, String[] excepted) {
        boolean[][] grid = board.getGrid();
        int width = board.getSize().getWidth();
        int height = board.getSize().getHeight();
        assertEquals(width, excepted[0].length());
        assertEquals(height, excepted.length);
        boolean gridEqualsToExpected = true;
        StringBuilder consoleView = new StringBuilder();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Boolean cell = grid[x][y];
                char ch = excepted[y].charAt(x);
                assertNotNull(cell);
                boolean cellEmpty = !cell;
                boolean cellEquals = cellEmpty == (ch == '.');
                gridEqualsToExpected &= cellEquals;
                if (cellEquals)
                    consoleView.append(cellEmpty ? '.' : '#');
                else
                    consoleView.append(cellEmpty ? '!' : '?');
            }
            consoleView.append('\n');
        }
        if (!gridEqualsToExpected)
            System.out.println(consoleView.toString());
        assertTrue(gridEqualsToExpected);
    }
    public static void checkTetrominoState(Tetromino tetromino, String[] excepted) {
        Point[] parts = tetromino.getParts();
        int width = tetromino.getBoundingBox().getWidth();
        int height = tetromino.getBoundingBox().getHeight();
        assertEquals(width, excepted[0].length());
        assertEquals(height, excepted.length);
        boolean gridEequalsToExpected = true;
        StringBuilder consoleView = new StringBuilder();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Point cell = findPoint(parts, x, y);
                char ch = excepted[y].charAt(x);
                boolean cellEmpty = cell == null;
                boolean cellEquals = cellEmpty == (ch == '.');
                gridEequalsToExpected &= cellEquals;
                if (cellEquals)
                    consoleView.append(cellEmpty ? '.' : '#');
                else
                    consoleView.append(cellEmpty ? '!' : '?');
            }
            consoleView.append('\n');
        }
        if (!gridEequalsToExpected)
            System.out.println(consoleView.toString());
        assertTrue(gridEequalsToExpected);
    }
    public static void runLater(int delay, Runnable task) {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException e) {
            // Must never happen, because the main thread is not notified.
        }
        task.run();
    }

    private static Point findPoint(Point[] list, int x, int y) {
        for (Point point : list) {
            if (point.getX() == x && point.getY() == y)
                return point;
        }
        return null;
    }
}