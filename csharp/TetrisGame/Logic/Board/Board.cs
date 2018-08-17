using System;
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
            Point basePos = tetromino.GetPosition();
            foreach (Point partOffset in tetromino.GetCurrentData().Parts)
            {
                Point position = basePos + partOffset;
                Grid[position.x, position.y] = true;
            }
        }
        public bool CanAddTetromino(Tetromino tetromino, Point from)
        {
            TetrominoData tetrominoData = tetromino.GetCurrentData();
            if (!IsBoxInsideGrid(from, tetrominoData.BoundingBox))
                return false;
            foreach (Point partOffset in tetrominoData.Parts)
            {
                Point position = from + partOffset;
                if (Grid[position.x, position.y])
                    return false;
            }
            return true;
        }
        public void RemoveFullRows()
        {
            throw new NotImplementedException();
        }

        private bool IsBoxInsideGrid(Point boxPosition, Dimension boxSize)
        {
            return (boxPosition.x >= 0)
                && (boxPosition.y >= 0)
                && ((boxPosition.x + boxSize.width) <= Size.width)
                && ((boxPosition.y + boxSize.height) <= Size.height);
        }
    }
}