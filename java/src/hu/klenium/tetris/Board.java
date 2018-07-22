package hu.klenium.tetris;

import hu.klenium.tetris.view.BoardView;

/**
 *
 */
public class Board {
    /**
     *
     */
    private final int rows;
    /**
     *
     */
    private final int columns;
    /**
     *
     */
    private final BoardView view;
    /**
     * The board's data. This array is indexed by height then widht.
     */
    private final BoardCell[][] table;

    /**
     * Initializes a new board of a game.
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
                table[i][j] = new BoardCell(j, i);
        }
        updateView();
    }
    /**
     *
     * @return
     */
    public int getHeight() {
        return rows;
    }
    /**
     *
     * @return
     */
    public int getWidth() {
        return columns;
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
        if (fromX < 0 || fromX + data.getWidth() > columns ||
            fromY < 0 || fromY + data.getHeight() > rows)
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
        TetrominoData data = tetromino.getData();
        for (TetrominoPart part : data.getParts()) {
            int x = baseX + part.getOffsetX();
            int y = baseY + part.getOffsetY();
            table[y][x].setView(part.getView());
        }
        updateView();
    }
    /**
     * Called when a tetromino falls down, and so it is possible a row will be full.
     * Full rows need to be removed from the board. If a row is removed, all other rows
     * above it will fall down one row too.
     */
    public void removeFullRows() {
        boolean isRowFull;
        for (int i = 0; i < rows; ++i) {
            isRowFull = true;
            for (int j = 0; j < columns && isRowFull; ++j) {
                if (table[i][j].isEmpty())
                    isRowFull = false;
            }
            if (isRowFull) {
                for (int j = i; j > 0; --j) {
                    for (int k = 0; k < columns; ++k)
                        table[j][k].setView(table[j - 1][k].getView());
                }
                for (int j = 0; j < columns; ++j)
                    table[0][j].clear();
            }
        }
        updateView();
    }

    /**
     * Called when the board's data is updated and need to be displayed again.
     * Passes the needed data to the view, which will draw it.
     */
    private void updateView() {
        view.update(table);
    }
}