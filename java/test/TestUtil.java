import hu.klenium.tetris.logic.board.Board;
import hu.klenium.tetris.logic.board.BoardCell;
import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.logic.tetromino.TetrominoData;
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
    public static void checkBoardState(Board board, String[] excepted) {
        BoardCell[][] grid = board.getGrid();
        int width = board.getSize().width;
        int height = board.getSize().height;
        assertEquals(width, excepted[0].length());
        assertEquals(height, excepted.length);
        boolean gridEqualsToExpected = true;
        StringBuilder consoleView = new StringBuilder();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                BoardCell cell = grid[x][y];
                char ch = excepted[y].charAt(x);
                assertNotNull(cell);
                boolean cellEmpty = cell.isEmpty();
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
        TetrominoData data = tetromino.getCurrentData();
        Point[] parts = data.parts;
        int width = data.boundingBox.width;
        int height = data.boundingBox.height;
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

    private static Point findPoint(Point[] list, int x, int y) {
        for (Point point : list) {
            if (point.x == x && point.y == y)
                return point;
        }
        return null;
    }
}