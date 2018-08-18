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
        private readonly PeriodicTask gravity;
        public TetrisGame(Dimension size, int fallingSpeed)
        {
            board = new Board(size);
            gravity = new PeriodicTask(() => HandleCommand(Command.FALL), fallingSpeed);
        }
        public void Start()
        {
            isRunning = GenerateNextTetromino();
            if (isRunning)
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
                    RotatetTetromino();
                    break;
                case Command.MOVE_LEFT:
                    MoveTetrominoLeft();
                    break;
                case Command.MOVE_RIGHT:
                    MoveTetrominoRight();
                    break;
                case Command.MOVE_DOWN:
                    gravity.Reset();
                    MoveTetrominoDown();
                    break;
                case Command.FALL:
                    MoveTetrominoDown();
                    break;
                case Command.DROP:
                    bool lastMovedDown;
                    do
                    {
                        lastMovedDown = MoveTetrominoDown();
                    } while (lastMovedDown);
                    break;
            }
        }

        private void TetrominoLanded()
        {
            board.AddTetromino(fallingTetromino);
            board.RemoveFullRows(fallingTetromino.Position.y, fallingTetromino.BoundingBox.height);
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
        private bool MoveTetrominoLeft()
        {
            return fallingTetromino.MoveLeft();
        }
        private bool MoveTetrominoDown()
        {
            bool moved = fallingTetromino.MoveDown();
            if (!moved)
                TetrominoLanded();
            return moved;
        }
        private bool MoveTetrominoRight()
        {
            return fallingTetromino.MoveRight();
        }
        private bool RotatetTetromino()
        {
            return fallingTetromino.RotateRight();
        }
    }
}