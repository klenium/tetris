using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;

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
            isRunning = GenerateNextTetromino();
            if (isRunning)
            {
                gravity.Start();
            }
        }
        private void Stop()
        {
            isRunning = false;
        }

        public void HandleCommand(Command command)
        {
            if (!isRunning)
                return;
            bool stateChanged = false;
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
                    stateChanged = fallingTetromino.MoveDown();
                    if (!stateChanged)
                        TetrominoLanded();
                    break;
                case Command.FALL:
                    stateChanged = fallingTetromino.MoveDown();
                    if (!stateChanged)
                        TetrominoLanded();
                    break;
                case Command.DROP:
                    bool lastMovedDown;
                    do
                    {
                        lastMovedDown = fallingTetromino.MoveDown();
                    } while (lastMovedDown);
                    TetrominoLanded();
                    break;
            }
        }

        private void TetrominoLanded()
        {
            board.AddTetromino(fallingTetromino);
            board.RemoveFullRows();
            bool tetrominoAdded = GenerateNextTetromino();
            if (tetrominoAdded)
                gravity.Reset();
            else
                Stop();
        }
        private bool GenerateNextTetromino()
        {
            int type = GetNextTetrominoType();
            fallingTetromino = new Tetromino(type, board);
            return fallingTetromino.MoveToInitialPos();
        }
        protected virtual int GetNextTetrominoType()
        {
            return Random.FromRange(0, 6);
        }
    }
}