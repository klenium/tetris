using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;

namespace hu.klenium.tetris.logic.board
{
    public class Board
    {
        public Dimension Size { get; }
        public bool[,] Grid { get; private set; }
        public Board(Dimension size)
        {
            this.Size = size;
            this.Grid = new bool[size.width, size.height];
        }
        public void AddTetromino(Tetromino tetromino)
        {
            foreach (Point partOffset in tetromino.CurrentParts)
            {
                Point position = tetromino.Position + partOffset;
                Grid[position.x, position.y] = true;
            }
        }
        public bool CanAddTetromino(Tetromino tetromino, Point from)
        {
            if (!IsBoxInsideGrid(from, tetromino.BoundingBox))
                return false;
            foreach (Point partOffset in tetromino.CurrentParts)
            {
                Point position = from + partOffset;
                if (Grid[position.x, position.y])
                    return false;
            }
            return true;
        }
        public void RemoveFullRows()
        {
            for (int currentRow = 0; currentRow < Size.height; ++currentRow)
            {
                if (IsRowFull(currentRow))
                    MoveDownBoardsUpperPart(currentRow);
            }
        }

        private bool IsBoxInsideGrid(Point boxPosition, Dimension boxSize)
        {
            return !(boxPosition < new Point(0, 0))
                && !((boxPosition + boxSize) > new Point(Size));
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