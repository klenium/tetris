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
            throw new NotImplementedException();
        }
    }
}