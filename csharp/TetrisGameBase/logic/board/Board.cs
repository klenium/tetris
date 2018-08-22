using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;

namespace hu.klenium.tetris.logic.board
{
    public class Board
    {
        public bool[,] Grid { get; private set; }
        public Dimension Size
        {
            get { return new Dimension(Grid.GetLength(0), Grid.GetLength(1)); }
        }
        public Board(Dimension size)
        {
            this.Grid = new bool[size.width, size.height];
        }
        public void AddTetromino(Tetromino tetromino)
        {
            foreach (Point partOffset in tetromino.Parts)
            {
                (int x, int y) = tetromino.Position + partOffset;
                Grid[x, y] = true;
            }
        }
        public bool CanAddTetromino(Tetromino tetromino, Point from)
        {
            if (!IsBoxInsideGrid(from, tetromino.BoundingBox))
                return false;
            foreach (Point partOffset in tetromino.Parts)
            {
                (int x, int y) = from + partOffset;
                if (Grid[x, y])
                    return false;
            }
            return true;
        }
        public void RemoveFullRows(int startRow, int numOfRowsToCheck)
        {
            for (int currentRow = startRow; currentRow < (startRow + numOfRowsToCheck); ++currentRow)
            {
                if (IsRowFull(currentRow))
                    MoveDownBoardsUpperPart(currentRow);
            }
        }

        private bool IsBoxInsideGrid(Point boxPosition, Dimension boxSize)
        {
            return !(boxPosition < (0, 0))
                && !((boxPosition + boxSize) > Size);
        }
        private bool IsRowFull(int rowIndex)
        {
            for (int x = 0; x < Size.width; ++x)
            {
                if (!Grid[x, rowIndex])
                    return false;
            }
            return true;
        }
        private void MoveDownBoardsUpperPart(int startRowIndex)
        {
            for (int y = startRowIndex; y > 0; --y)
            {
                for (int x = 0; x < Size.width; ++x)
                    Grid[x, y] = Grid[x, y - 1];
            }
            for (int x = 0; x < Size.width; ++x)
                Grid[x, 0] = false;
        }
    }
}