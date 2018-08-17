using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using hu.klenium.tetris.Util;
using System;

namespace hu.klenium.tetris.logic
{
    public class TetrisGame
    {
        protected bool isRunning;
        protected readonly Board board;
        protected Tetromino fallingTetromino = null;
        private PeriodicTask gravity;
        public TetrisGame(Dimension size, int fallingSpeed)
        {
            board = new Board(size);
            gravity = new PeriodicTask(() => HandleCommand(Command.FALL), fallingSpeed);
        }
        public void Start()
        {
            isRunning = true;
            fallingTetromino = new Tetromino(3, board);
            fallingTetromino.MoveToInitialPos();
            gravity.Start();
        }
        private void Stop()
        {
            isRunning = false;
        }

        public void HandleCommand(Command command)
        {
            if (!isRunning)
                return;
            switch (command)
            {
                case Command.ROTATE:
                    fallingTetromino.RotateRight();
                    break;
                case Command.MOVE_LEFT:
                    fallingTetromino.MoveLeft();
                    break;
                case Command.MOVE_RIGHT:
                    fallingTetromino.MoveRight();
                    break;
                case Command.MOVE_DOWN:
                    gravity.Reset();
                    fallingTetromino.MoveDown();
                    break;
                case Command.FALL:
                    fallingTetromino.MoveDown();
                    break;
                case Command.DROP:
                    bool lastMovedDown;
                    do
                    {
                        lastMovedDown = fallingTetromino.MoveDown();
                    } while (lastMovedDown);
                    break;
            }
        }
    }
}