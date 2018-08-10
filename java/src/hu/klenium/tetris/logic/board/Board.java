package hu.klenium.tetris.logic.board;

import hu.klenium.tetris.logic.tetromino.Tetromino;
import hu.klenium.tetris.logic.tetromino.TetrominoData;
import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;

/**
 * Board is a grid, it stores fallen tetromino parts.
 *
 * Each game has one board, this is the main display area. When a
 * tetromino can't move down any more, its parts are added to the board.
 * These parts aren't moveable, the player can't control them in any
 * ways. When a row of the board is full (ie. each cell contains a
 * tetromino part), that row is removed, and every rows above it are
 * moved down by one row.
 */
public class Board {
    /**
     * Number of rows and columns of the grid.
     */
    private final Dimension gridSize;
    /**
     * Contains previously fallen tetrominoes' parts.
     *
     * A cell may be empty, ie. the cell doesn't contain any tetromino
     * part (see {@link EmptyBoardCell}), or used/filled, when it stores one
     * part (see {@link FilledBoardCell}). The falling tetromino's parts can't
     * be moved over any used cells, a part can't overlap another part. When a
     * tetromino lands (ie. can't move down anymore), its parts are added to
     * the grid.
     * <br>
     * This array is indexed by coordinates, first X, then Y. Its size is
     * stored in {@link #gridSize}.
     */
    private final BoardCell[][] grid;

    /**
     * Initializes a new board for a new game.
     * @param size The board's size (rows/columns).
     */
    public Board(Dimension size) {
        this.gridSize = size;
        this.grid = new BoardCell[size.width][size.height];
        for (int x = 0; x < size.width; ++x) {
            for (int y = 0; y < size.height; ++y)
                grid[x][y] = new EmptyBoardCell();
        }
    }
    /**
     * Tells the board's size. It's used to calculate new falling
     * tetrominoes' initial position (which is related to the board's
     * centre).
     * @return The board's size (rows/columns).
     * @see #gridSize
     */
    public Dimension getSize() {
        return gridSize;
    }
    /**
     * Gives access to the grid: used by view only!
     * @return The board's grid.
     * @see #grid
     */
    public BoardCell[][] getGrid() {
        return grid;
    }

    /**
     * Checks if the tetromino in its current state (excluding its
     * position) could be added to the grid at a specificed position.
     * @param tetromino The tetromino which will be tested.
     * @param from Testing position from where the tetromino wanted to be
     *             added to the grid.
     * @return If the tetromino's bounding box wouldn't fit into the
     *         grid, or at least one of the grid's cells which would be
     *         used by the tetromino isn't empty returns false, otherwise
     *         returns true.
     */
    public boolean canAddTetromino(Tetromino tetromino, Point from) {
        TetrominoData tetrominoData = tetromino.getCurrentData();
        if (!isBoxInsideGrid(from, tetrominoData.boundingBox))
            return false;
        for (Point partOffset : tetrominoData.parts) {
            Point position = from.add(partOffset);
            if (!grid[position.x][position.y].isEmpty())
                return false;
        }
        return true;
    }
    /**
     * Adds a landed tetromino's parts to the board. Empty cells will be
     * replaced with filled cells.
     * The tetromino should be destroyed after it is added to the board.
     * @param tetromino The landed tetromino.
     */
    public void addTetromino(Tetromino tetromino) {
        Point base = tetromino.getPosition();
        int type = tetromino.getType();
        for (Point partOffset : tetromino.getCurrentData().parts) {
            Point position = base.add(partOffset);
            grid[position.x][position.y] = new FilledBoardCell(type);
        }
    }
    /**
     * Called when a tetromino lands, and so it's possible a row will be full.
     * Full rows need to be removed from the board. If a row is removed, all other rows
     * above it will be moved down one row too, and the upper row will be
     * filled with empty cells.
     */
    public void removeFullRows() {
        for (int currentRow = 0; currentRow < gridSize.height; ++currentRow) {
            if (isRowFull(currentRow))
                moveDownBoardsUpperPart(currentRow);
        }
    }

    /**
     * Checks if a rectangle is inside the board's area.
     * It is used to prevent invalid states of a tetromino, because
     * they can't moved outside of the board.
     * @param boxPosition The rectangle's top-left position.
     * @param boxSize The rectangle's size.
     * @return True if the tetromino's rectangle is in the grid's rectangle,
     *         false otherwise.
     */
    private boolean isBoxInsideGrid(Point boxPosition, Dimension boxSize) {
        return (boxPosition.x >= 0)
            && (boxPosition.y >= 0)
            && ((boxPosition.x + boxSize.width) <= gridSize.width)
            && ((boxPosition.y + boxSize.height) <= gridSize.height);
    }
    /**
     * Determines whether the specified row is full.
     * @param rowIndex The number of that row which is tested.
     * @return True when at least one cell in that row is empty,
     *         or false if all cells are used.
     */
    private boolean isRowFull(int rowIndex) {
        for (int x = 0; x < gridSize.width; ++x) {
            if (grid[x][rowIndex].isEmpty())
                return false;
        }
        return true;
    }
    /**
     * Copies and adds new rows. When a row is removed from the board,
     * each row above if will be moved down by one row, and each cells in the
     * upper row will be replaced with empty cells. That's how Tetris works.
     * @param startRowIndex The number (index) of the row which was/will be
     *                      removed from the board.
     */
    private void moveDownBoardsUpperPart(int startRowIndex) {
        for (int y = startRowIndex; y > 0; --y) {
            for (int x = 0; x < gridSize.width; ++x)
                grid[x][y] = grid[x][y - 1];
        }
        for (int x = 0; x < gridSize.width; ++x)
            grid[x][0] = new EmptyBoardCell();
    }
}