using hu.klenium.tetris.Util;
using System;

namespace hu.klenium.tetris.Logic.Tetromino
{
    public class Tetromino
    {
        private readonly TetrominoData[] parts;
        private Point position = new Point(0, 0);
        private int rotation = 0;
        public Tetromino(int type)
        {
            parts = TetrominoDataFactory.GetData(type);
        }
        public TetrominoData GetCurrentData()
        {
            return parts[rotation];
        }
        public Point GetPosition()
        {
            return position;
        }

        // TODO: neews board, will implement later
        // Tempolary new position applies to a T-type tetromino, in a 5*y board.
        public bool MoveToInitialPos()
        {
            position = new Point(2, 0);
            return false;
        }
        public bool MoveLeft()
        {
            position += new Point(-1, 0);
            return false;
        }
        public bool MoveDown()
        {
            position += new Point(0, 1);
            return false;
        }
        public bool MoveRight()
        {
            position += new Point(1, 0);
            return false;
        }
    }
}