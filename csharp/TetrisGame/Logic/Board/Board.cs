using System;
using hu.klenium.tetris.Util;

namespace hu.klenium.tetris.Logic.Board
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
        public void AddTetromino(Tetromino.Tetromino tetromino)
        {
            Point basePos = tetromino.GetPosition();
            foreach (Point partOffset in tetromino.GetCurrentData().Parts)
            {
                Point position = basePos + partOffset;
                Grid[position.x, position.y] = true;
            }
        }
    }
}