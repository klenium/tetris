package hu.klenium.tetris;

import hu.klenium.tetris.view.BoardView;

public class Board {
    private final int rows;
    private final int columns;
    private BoardCell[][] table;
    private final BoardView view;

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
    public int getHeight() {
        return rows;
    }
    public int getWidth() {
        return columns;
    }

    public boolean canAddTetromino(Tetromino tetromino, int fromX, int fromY) {
        TetrominoData data = tetromino.getPolyominoData();
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
    public void addTetromino(Tetromino tetromino) {
        int baseX = tetromino.getPosX();
        int baseY = tetromino.getPosY();
        TetrominoData data = tetromino.getPolyominoData();
        for (TetrominoPart part : data.getParts()) {
            int x = baseX + part.getOffsetX();
            int y = baseY + part.getOffsetY();
            table[y][x].setView(part.getView());
        }
        updateView();
    }
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

    private void updateView() {
        view.update(table);
    }
}