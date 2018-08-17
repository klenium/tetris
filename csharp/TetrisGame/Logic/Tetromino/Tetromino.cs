using System;

namespace hu.klenium.tetris.Logic.Tetromino
{
    public class Tetromino
    {
        private readonly TetrominoData[] parts;
        private int rotation = 0;
        public Tetromino(int type)
        {
            parts = TetrominoDataFactory.GetData(type);
        }
        public TetrominoData getCurrentData()
        {
            return parts[rotation];
        }
    }
}