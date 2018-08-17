using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using hu.klenium.tetris.Util;
using System;

namespace hu.klenium.tetris.logic
{
    public class TetrisGame
    {
        protected readonly Board board;
        protected Tetromino fallingTetromino;
        private PeriodicTask gravity;
        public TetrisGame(Dimension size, int fallingSpeed)
        {
            board = new Board(size);
            gravity = new PeriodicTask(() => HandleCommand(Command.FALL), fallingSpeed);
        }
        public void Start()
        {
            fallingTetromino = new Tetromino(3, board);
            fallingTetromino.MoveToInitialPos();
            gravity.Start();
        }
        public void HandleCommand(Command command)
        {
            switch (command)
            {
                case Command.FALL:
                    fallingTetromino.MoveDown();
                    break;
            }
        }
    }
}