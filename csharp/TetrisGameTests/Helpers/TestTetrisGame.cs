using hu.klenium.tetris.logic;
using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using System.Collections.Generic;

namespace TetrisGameTests.Helpers
{
    public class TestTetrisGame : TetrisGame
    {
        public Board Board
        {
            get { return board; }
        }
        public Tetromino Tetromino
        {
            get { return fallingTetromino; }
        }
        public bool State
        {
            get { return isRunning; }
        }
        public TestTetrisGame(Dimension size, List<int> tetrominoTypes, int fallingSpeed)
            :base(size, fallingSpeed)
        {

        }
    }
}