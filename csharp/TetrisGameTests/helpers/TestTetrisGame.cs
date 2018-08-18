using hu.klenium.tetris.logic;
using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using System.Collections.Generic;
using System.Linq;

namespace TetrisGameTests.Helpers
{
    public class TestTetrisGame : TetrisGame
    {
        private List<int> tetrominoTypes;
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
            this.tetrominoTypes = tetrominoTypes;
        }
        protected override int GetNextTetrominoType()
        {
            int type = tetrominoTypes.First();
            tetrominoTypes.RemoveAt(0);
            return type;
        }
    }
}