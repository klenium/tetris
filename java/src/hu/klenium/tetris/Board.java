package hu.klenium.tetris;

import hu.klenium.tetris.view.BoardCell;
import hu.klenium.tetris.view.BoardView;

/**
 * Board is a grid, it stores the fallen tetrominoes' parts.
 *
 * Each game has one board, this is the main display area. When a
 * tetromino can't move down any more, it is added to the board.
 * These parts are not moveable, the player can't control them in any
 * ways. When a row of the board is full (ie. each cell contains a
 * tetromino part), that row is removed, and every rows inside it are
 * moved down by one row.
 */
public class Board {
    /**
     * The rows in the board's table.
     */
    private final int rows;
    /**
     * The columns in the board's table.
     */
    private final int columns;
    /**
     * The view of the board is used to display its table.
     */
    private final BoardView view;
    /**
     * Contains previously fallen tetrominoes' parts.
     * This array is indexed by height then width.
     */
    private final BoardCell[][] table;

    /**
     * Initializes a new board for a new game.
     * @param rows Height of the board.
     * @param cols Width of the board.
     * @param view The board's view used to display it.
     */
    public Board(int rows, int cols, BoardView view) {
        this.rows = rows;
        this.columns = cols;
        this.view = view;
        this.table = new BoardCell[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j)
                table[i][j] = new BoardCell();
        }
        updateView();
    }
    public int getHeight() {
        return rows;
    }
    public int getWidth() {
        return columns;
    }
    public BoardCell[][] getCells() {
        return table;
    }

    /**
     * Checks if the tetromino in this state could be added to the board
     * at a specificed position.
     * @param tetromino The tetromino which will be tested.
     * @param fromX X coordinate where the tetromino is wanted to be added (left side).
     * @param fromY Y coordinate where the tetromino is wanted to be added (top side).
     * @return If each cell of the board are empty which would be used by the tetromino,
     *         return true, if the tetromino can't move to the specificed position,
     *         returns false.
     */
    public boolean canAddTetromino(Tetromino tetromino, int fromX, int fromY) {
        TetrominoData data = tetromino.getData();
        if (fromX < 0 || (fromX + data.getWidth()) > columns ||
            fromY < 0 || (fromY + data.getHeight()) > rows)
            return false;
        for (TetrominoPart part : data.getParts()) {
            int x = fromX + part.getOffsetX();
            int y = fromY + part.getOffsetY();
            if (!table[y][x].isEmpty())
                return false;
        }
        return true;
    }
    /**
     * Adds a fallen tetromino's parts to the board.
     * @param tetromino The fallen tetromino.
     */
    public void addTetromino(Tetromino tetromino) {
        int baseX = tetromino.getPosX();
        int baseY = tetromino.getPosY();
        for (TetrominoPart part : tetromino.getData().getParts()) {
            int x = baseX + part.getOffsetX();
            int y = baseY + part.getOffsetY();
            table[y][x].setView(part.getView());
        }
        updateView();
    }
    /**
     * Called when a tetromino falls down, and so it's possible a row will be full.
     * Full rows need to be removed from the board. If a row is removed, all other rows
     * above it will fall down one row too.
     */
    public void removeFullRows() {
        for (int i = 0; i < rows; ++i) {
            boolean isRowFull = true;
            for (int j = 0; j < columns && isRowFull; ++j) {
                if (table[i][j].isEmpty())
                    isRowFull = false;
            }
            if (isRowFull) {
                for (int j = i; j > 0; --j) {
                    for (int k = 0; k < columns; ++k)
                        table[j] = table[j - 1];
                }
                for (int j = 0; j < columns; ++j)
                    table[0][j] = new BoardCell();
            }
        }
        updateView();
    }

    /**
     * Called when the board's data is updated and need to be displayed again.
     * Passes the needed data to the view, which will draw it.
     */
    private void updateView() {
        view.update(table, columns, rows);
    }
}